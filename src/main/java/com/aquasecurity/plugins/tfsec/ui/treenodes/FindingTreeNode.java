package com.aquasecurity.plugins.tfsec.ui.treenodes;

import com.aquasecurity.plugins.tfsec.icons.TfsecIcons;
import com.aquasecurity.plugins.tfsec.model.Finding;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

class FindingTreeNode extends DefaultMutableTreeNode implements TfsecTreeNode {

    public final Finding finding;

    public FindingTreeNode(Finding finding) {
        super(finding);
        this.finding = finding;
    }

    @Override
    public Icon getIcon() {
        return TfsecIcons.Tfsec;
    }

    @Override
    public String getTitle() {
        return this.finding.LongID;
    }

    @Override
    public String getTooltip() {
        return this.finding.RuleDescription;
    }
}
