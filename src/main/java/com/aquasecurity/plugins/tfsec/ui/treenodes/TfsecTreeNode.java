package com.aquasecurity.plugins.tfsec.ui.treenodes;

import javax.swing.*;

public interface TfsecTreeNode {
    Icon getIcon();

    String getTitle();

    String getTooltip() ;
}
