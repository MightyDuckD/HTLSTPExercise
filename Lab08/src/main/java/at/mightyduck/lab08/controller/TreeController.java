/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.mightyduck.lab08.controller;

import at.mightyduck.lab08.model.Schueler;
import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author Simon
 */
@Named("treecon")
@SessionScoped
public class TreeController implements Serializable {

    @Inject
    private SchuelerController schuelerCon;

    private TreeNode root, schuelerAH, schuelerIZ, selectedNode;
    private Schueler selectedSchueler;

    @PostConstruct
    public void init() {
        root = new DefaultTreeNode("Root", null);
        schuelerAH = new DefaultTreeNode("Schüler A-H", root);
        schuelerIZ = new DefaultTreeNode("Schüler I-Z", root);
        refreshTree();
    }

    public void refreshTree() {
//        boolean ah = schuelerAH.isExpanded();
//        boolean iz = schuelerIZ.isExpanded();
        schuelerAH.getChildren().clear();
        schuelerIZ.getChildren().clear();
        for (Schueler schueler : schuelerCon.getSchueler()) {
            new DefaultTreeNode(
                    "schueler",
                    schueler,
                    schueler.getVorname().matches("[A-Ha-h].*") ? schuelerAH : schuelerIZ
            );
        }
        Comparator<TreeNode> byKN = new Comparator<TreeNode>() {
            @Override
            public int compare(TreeNode o1, TreeNode o2) {
                Schueler s1 = (Schueler) o1.getData();
                Schueler s2 = (Schueler) o2.getData();
                return s1.getNr() - s2.getNr();
            }
        };
        schuelerAH.getChildren().sort(byKN);
        schuelerIZ.getChildren().sort(byKN);
//        schuelerAH.setExpanded(ah);
//        schuelerIZ.setExpanded(iz);
    }

    public TreeNode getRoot() {
        return root;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public Schueler getSelectedSchueler() {
        return selectedSchueler;
    }

    public void onNodeSelect(NodeSelectEvent event) {
        TreeNode me = event.getTreeNode();
        if (me.equals(schuelerAH) || me.equals(schuelerIZ)) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bereichsknoten", "Es befinden sich " + me.getChildCount() + " Schüler in dieser Komponente");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else {
            selectedSchueler = (Schueler) me.getData();
        }
    }

    public void addNewSchueler() {
        this.schuelerCon.addNewSchueler();
        this.refreshTree();
    }

    public void nodeExpand(NodeExpandEvent event) {
        event.getTreeNode().setExpanded(true);
    }

    public void nodeCollapse(NodeCollapseEvent event) {
        event.getTreeNode().setExpanded(false);
    }
}
