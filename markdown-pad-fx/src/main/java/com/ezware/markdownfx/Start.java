package com.ezware.markdownfx;

import java.util.Arrays;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.controlsfx.control.action.AbstractAction;
import org.controlsfx.control.action.ActionGroup;
import org.controlsfx.control.action.ActionUtils;
import org.controlsfx.control.action.ActionUtils.ActionTextBehavior;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

public class Start extends javafx.application.Application implements DocumentEditorProvider {

    private TabPane tabs = new TabPane();

    public static void main(String[] args) {
        launch(args);
    }
    
    @Override 
    public void start(Stage primaryStage) {
        
        createDocumentEditor();
        
        ActionNewEditor  actionNewEditor  = new ActionNewEditor();
        ActionOpenEditor actionOpenEditor = new ActionOpenEditor();
        ActionSaveEditor actionSaveEditor = new ActionSaveEditor();
        
        ActionCutText actionCutText  = new ActionCutText();
        ActionCopyText actionCopyText = new ActionCopyText();
        ActionPasteText actionPasteText = new ActionPasteText();
        
        MenuBar menuBar = ActionUtils.createMenuBar( Arrays.asList( 
          new ActionGroup("File", actionNewEditor, actionOpenEditor, actionSaveEditor,
              ActionUtils.ACTION_SEPARATOR,
              new ActionAppExit(primaryStage)),       
          new ActionGroup("Edit", actionCutText, actionCopyText, actionPasteText)       
        ));
        
        menuBar.setUseSystemMenuBar(true); // Mac OSX support
        
        
        ToolBar toolBar = ActionUtils.createToolBar( Arrays.asList(
            actionNewEditor, actionOpenEditor, actionSaveEditor,
            ActionUtils.ACTION_SEPARATOR,
            actionCutText, actionCopyText, actionPasteText
        ), ActionTextBehavior.HIDE);
        
        BorderPane content = new BorderPane();
        content.setTop(new VBox(menuBar, toolBar));
        content.setCenter(tabs);
        content.setPrefSize(1000, 800);
//        Pane statusBar = new Pane( new Label("v 1.0.0"));
//        statusBar.setPadding(new Insets(5,5,5,5));
//        content.setBottom(statusBar);
        
        primaryStage.setTitle("Markdown Pad FX");
        primaryStage.setScene(new Scene(content));
        primaryStage.show();
    }
    
    private DocumentEditor createDocumentEditor() {
        final DocumentEditor editor = new DocumentEditor("");
        Tab tab = new Tab( "New Document" );
        tab.setContent(editor);
        tab.setOnCloseRequest(new EventHandler<Event>() {
            
            public void handle(Event e) {
                
               Dialogs dlg = Dialogs.create().title("Confirmation").message("Close tab?");
               if ( editor.isDirty() && dlg.showConfirm() != Dialog.Actions.YES ){
                  e.consume();
               }
                
            }
        });
        
        tabs.getTabs().add(tab);
        tabs.getSelectionModel().select(tab);
        return editor;
    }


    public DocumentEditor getDocumentEditor() {
        Tab tab = tabs.getSelectionModel().getSelectedItem();
        return  tab == null? null: (DocumentEditor) tab.getContent();
    }
    
    
/////// Actions //////////////////////////////////////////////////////////////////////////////    

    class ActionNewEditor extends AbstractAction {

        public ActionNewEditor() {
            super("New");
            setGraphic(  new Image("/images/file2.png") );
            setLongText("Create New Document");
        }

        @Override public void execute(ActionEvent e) {
            createDocumentEditor();
        }
        
    }   
    
    class ActionOpenEditor extends AbstractAction {

        public ActionOpenEditor() {
            super("Open");
            setGraphic(  new Image("/images/folder-open.png") );
            setLongText("Open Existing Document");
        }

        @Override public void execute(ActionEvent e) {
        }
        
    }    
    
    class ActionSaveEditor extends AbstractAction {

        public ActionSaveEditor() {
            super("Save");
            setGraphic(  new Image("/images/disk.png") );
            setLongText("Save Document");
        }

        @Override public void execute(ActionEvent e) {
        }
        
    }   
    
    class ActionCutText extends AbstractAction {

        public ActionCutText() {
            super("Cut");
            setGraphic(  new Image("/images/scissors.png") );
            setLongText("Cut");
        }

        @Override public void execute(ActionEvent e) {
        }
        
    }   
    
    class ActionCopyText extends AbstractAction {

        public ActionCopyText() {
            super("Copy");
            setGraphic(  new Image("/images/copy.png") );
            setLongText("Copy");
        }

        @Override public void execute(ActionEvent e) {
        }
        
    }   
    
    class ActionPasteText extends AbstractAction {

        public ActionPasteText() {
            super("Paste");
            setGraphic(  new Image("/images/paste2.png") );
            setLongText("Paste");
        }

        @Override public void execute(ActionEvent e) {
        }
        
    }       
    static class ActionAppExit extends AbstractAction {

        private Stage stage;
        
        public ActionAppExit( Stage stage ) {
            super("Exit");
            assert stage != null;
            this.stage = stage;
        }

        @Override public void execute(ActionEvent e) {
            stage.close();
        }
        
    }    
    
}


