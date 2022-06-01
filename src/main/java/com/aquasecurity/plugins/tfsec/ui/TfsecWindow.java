package com.aquasecurity.plugins.tfsec.ui;

import com.aquasecurity.plugins.tfsec.model.Finding;
import com.aquasecurity.plugins.tfsec.model.Findings;
import com.aquasecurity.plugins.tfsec.model.Location;
import com.aquasecurity.plugins.tfsec.model.Severity;
import com.aquasecurity.plugins.tfsec.ui.treenodes.FileTreeNode;
import com.aquasecurity.plugins.tfsec.ui.treenodes.SeverityTreeNode;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.ui.ScrollPaneFactory;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.treeStructure.Tree;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.file.Paths;
import java.util.List;

public class TfsecWindow extends SimpleToolWindowPanel {

    private final Project project;
    private final FindingsHelper findingsHelper;
    private Tree root;

    public TfsecWindow(Project project) {
        super(false, true);

        this.project = project;
        this.findingsHelper = new FindingsHelper();
        configureToolbar();
    }

    private void configureToolbar() {
        ActionManager actionManager = ActionManager.getInstance();

        DefaultActionGroup actionGroup = new DefaultActionGroup("ACTION_GROUP", false);

        actionGroup.add(actionManager.getAction("com.aquasecurity.plugins.tfsec.actions.RunScannerAction"));
        actionGroup.add(actionManager.getAction("com.aquasecurity.plugins.tfsec.actions.ClearResultsAction"));
        actionGroup.add(actionManager.getAction("com.aquasecurity.plugins.tfsec.actions.UpdateTfsecAction"));
        actionGroup.add(actionManager.getAction("com.aquasecurity.plugins.tfsec.actions.ShowTfsecSettingsAction"));

        ActionToolbar actionToolbar = actionManager.createActionToolbar("ACTION_TOOLBAR", actionGroup, true);
        actionToolbar.setOrientation(SwingConstants.VERTICAL);
        this.setToolbar(actionToolbar.getComponent());
    }

    @Override
    public @Nullable JComponent getContent() {
        return this.getComponent();
    }


    public void updateFindings(Findings findings) {
        if (findings == null) {
            this.root = null;
            return;
        }
        findings.setBasePath(project.getBasePath());
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Findings by severity");
        addFindings(findings, rootNode, Severity.CRITICAL);
        addFindings(findings, rootNode, Severity.HIGH);
        addFindings(findings, rootNode, Severity.MEDIUM);
        addFindings(findings, rootNode, Severity.LOW);

        this.root = new Tree(rootNode);
        root.putClientProperty("JTree.lineStyle", "Horizontal");
        root.setRootVisible(false);
        root.setCellRenderer(new FindingTreeRenderer());
        root.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                doMouseClicked(me);
            }
        });
    }

    private static void addFindings(Findings findings, DefaultMutableTreeNode top, Severity severity) {
        List<Finding> filteredFindings = findings.GetBySeverity(severity);
        if (filteredFindings.size() == 0) {
            return;
        }
        top.add(new SeverityTreeNode(filteredFindings, severity));
    }

    void doMouseClicked(MouseEvent me) {
        Object lastSelectedNode = root.getLastSelectedPathComponent();
        if (lastSelectedNode == null) {
            return;
        }
        if (lastSelectedNode instanceof FileTreeNode) {
            FileTreeNode node = (FileTreeNode) lastSelectedNode;
            Location findingLocation = node.getFindingLocation();
            if (findingLocation == null) {
                return;
            }

            this.findingsHelper.setFinding(node.getFinding());

            VirtualFile file = VirtualFileManager.getInstance().refreshAndFindFileByNioPath(Paths.get(findingLocation.Filename));
            if (file == null || !file.exists()) {
                return;
            }

            OpenFileDescriptor ofd = new OpenFileDescriptor(project, file, findingLocation.StartLine - 1, 0);
            Editor editor = FileEditorManager.getInstance(project).openTextEditor(ofd, true);
            if (editor == null) {
                return;
            }
            editor.getSelectionModel()
                    .setBlockSelection(new LogicalPosition(findingLocation.StartLine - 1, 0),
                            new LogicalPosition(findingLocation.EndLine - 1, 1000));
        }
    }

    public void redraw() {
        this.removeAll();
        if (this.root != null) {
            updateView();
        }

        configureToolbar();
        this.validate();
        this.repaint();
    }

    private void updateView() {
        JSplitPane splitPane = new JSplitPane(0);
        splitPane.setDividerSize(1);
        splitPane.add(new JBScrollPane(this.root));
        splitPane.add(new JBScrollPane(this.findingsHelper));
        this.add(splitPane);
    }

    public boolean hasFindings() {
        return this.root != null;
    }
}
