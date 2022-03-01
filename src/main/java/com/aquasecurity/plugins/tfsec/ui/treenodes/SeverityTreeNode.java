package com.aquasecurity.plugins.tfsec.ui.treenodes;

import com.aquasecurity.plugins.tfsec.icons.TfsecIcons;
import com.aquasecurity.plugins.tfsec.model.Finding;
import com.aquasecurity.plugins.tfsec.model.Severity;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SeverityTreeNode extends DefaultMutableTreeNode implements TfsecTreeNode {

    private final Severity severity;
    private final String title;
    private final Icon icon;

    public SeverityTreeNode(List<Finding> findings, Severity severity) {
        this.severity = severity;
        List<FindingTreeNode> visited = new ArrayList<>();
        for (Finding finding : findings) {
            if (visited.stream().noneMatch(v -> Objects.equals(v.finding.LongID, finding.LongID))) {
                visited.add(new FindingTreeNode(finding));
            }
            Optional<FindingTreeNode> findingNode = visited.stream().filter(vf -> Objects.equals(vf.finding.LongID, finding.LongID)).findFirst();
            if (findingNode.isPresent()) {
                FindingTreeNode node = findingNode.get();
                node.add(new FileTreeNode(finding));
            }
        }
        visited.forEach(this::add);

        switch (this.severity) {
            case CRITICAL:
                title = "Critical";
                icon = TfsecIcons.Critical;
                break;
            case HIGH:
                title = "High";
                icon = TfsecIcons.High;
                break;
            case MEDIUM:
                title = "Medium";
                icon = TfsecIcons.Medium;
                break;
            case LOW:
                title = "Low";
                icon = TfsecIcons.Low;
                break;
            default:
                title = "Unknown";
                icon = TfsecIcons.Low;
                break;
        }
    }

    public String getTitle() {
        return this.title;
    }

    public Icon getIcon() {
        return this.icon;
    }

    public String getTooltip() {
        return "";
    }
}
