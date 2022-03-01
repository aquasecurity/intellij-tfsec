package com.aquasecurity.plugins.tfsec.icons;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;


public interface TfsecIcons {
    Icon Tfsec = IconLoader.getIcon("/icons/tfsec.png", TfsecIcons.class);
    Icon Critical = IconLoader.getIcon("/icons/critical.svg", TfsecIcons.class);
    Icon High = IconLoader.getIcon("/icons/high.svg", TfsecIcons.class);
    Icon Medium = IconLoader.getIcon("/icons/medium.svg", TfsecIcons.class);
    Icon Low = IconLoader.getIcon("/icons/low.svg", TfsecIcons.class);
    Icon TerraformFile = IconLoader.getIcon("/icons/terraform.svg", TfsecIcons.class);

}
