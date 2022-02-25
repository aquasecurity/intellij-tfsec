package com.aquasecurity.plugins.tfsec.ui;

import com.aquasecurity.plugins.tfsec.model.Finding;
import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.UIUtil;
import org.jdesktop.swingx.JXHyperlink;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URI;
import java.util.List;

public class FindingsHelper extends JPanel {

    private Finding finding;

    public FindingsHelper() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(JBUI.Borders.empty(10));
    }

    public void setFinding(Finding finding) {
        this.finding = finding;
        updateHelp();
        this.validate();
        this.repaint();
    }

    private void updateHelp() {
        removeAll();
        if (this.finding == null) {
            return;
        }

        addHelpSection("", finding.RuleDescription);
        addHelpSection("ID", finding.LongID);
        addHelpSection("Severity", finding.Severity.toString());
        addHelpSection("Impact", finding.Impact);
        addHelpSection("Resolution", finding.Resolution);
        addHelpSection("Filename", finding.getRelativePath());

        if (finding.Links.size() > 0) {
            addLinkSection(finding.Links);
        }
    }

    private void addLinkSection(List<String> links) {
        JPanel section = new JPanel();
        section.setBorder(JBUI.Borders.empty(10));

        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));

        Font font = UIUtil.getLabelFont();
        Font headingFont = font.deriveFont(font.getStyle() | Font.BOLD);
        JLabel heading = new JLabel();
        heading.setFont(headingFont);
        heading.setText("Links");
        section.add(heading);

        links.forEach(link -> {
            JXHyperlink hyperlink = new JXHyperlink();
            hyperlink.setText(String.format("<html>%s</html>", link));
            hyperlink.setURI(URI.create(link));
            hyperlink.setToolTipText(link);
            hyperlink.setClickedColor(hyperlink.getUnclickedColor());
            hyperlink.setBorder(JBUI.Borders.emptyTop(5));
            section.add(hyperlink);

        });
        add(section);
    }

    private void addHelpSection(String title, String content) {
        JPanel section = new JPanel();
        section.setBorder(JBUI.Borders.empty(10));

        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));

        Font font = UIUtil.getLabelFont();
        Font headingFont = font.deriveFont(font.getStyle() | Font.BOLD);
        if (!title.isEmpty()) {
            JLabel heading = new JLabel();
            heading.setFont(headingFont);
            heading.setText(title);
            section.add(heading);
        }

        JLabel descriptionLabel = new JLabel();
        descriptionLabel.setFont(title.isEmpty() ? headingFont : font);
        descriptionLabel.setText(String.format("<html>%s</html>", content));
        descriptionLabel.setBorder(JBUI.Borders.emptyTop(5));
        section.add(descriptionLabel);
        add(section);
    }

}


