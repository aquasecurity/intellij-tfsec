package com.aquasecurity.plugins.tfsec.ui.treenodes;

import com.aquasecurity.plugins.tfsec.icons.TfsecIcons;
import com.aquasecurity.plugins.tfsec.model.Finding;
import com.aquasecurity.plugins.tfsec.model.Location;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class FileTreeNode extends DefaultMutableTreeNode implements TfsecTreeNode {

    private final Finding finding;

    public FileTreeNode(Finding finding) {
        this.finding = finding;
    }

    @Override
    public Icon getIcon() {
        return TfsecIcons.TerraformFile;
    }

    @Override
    public String getTitle() {
        String lineDetails = String.format("%d", finding.Location.StartLine);
        if (finding.Location.StartLine != finding.Location.EndLine) {
            lineDetails = String.format("%s-%d", lineDetails, finding.Location.EndLine);
        }
        String filename = finding.Location.Filename.replace(finding.getBasePath(), "");
        return String.format("%s:[%s]", filename, lineDetails);
    }

    @Override
    public String getTooltip() {
        return ""; }

        public Location getFindingLocation() {
        return finding.Location;
    }

    public Finding getFinding() {
        return this.finding;
    }
}
