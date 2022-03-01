package com.aquasecurity.plugins.tfsec.ui;

import com.aquasecurity.plugins.tfsec.ui.treenodes.TfsecTreeNode;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

class FindingTreeRenderer extends DefaultTreeCellRenderer {

    public FindingTreeRenderer() {
    }

    public Component getTreeCellRendererComponent(
            JTree tree,
            Object value,
            boolean sel,
            boolean expanded,
            boolean leaf,
            int row,
            boolean hasFocus) {

        super.getTreeCellRendererComponent(
                tree, value, sel,
                expanded, leaf, row,
                hasFocus);

        if (value instanceof TfsecTreeNode) {
            TfsecTreeNode node = (TfsecTreeNode) value;
            setIcon(node.getIcon());
            setText(node.getTitle());
            setToolTipText(node.getTooltip());
        }
        return this;
    }

}
