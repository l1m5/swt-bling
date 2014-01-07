package com.readytalk.examples.swt.widgets.buttons;

import com.readytalk.examples.swt.RunnableExample;
import com.readytalk.examples.swt.SwtBlingExample;
import com.readytalk.swt.util.FontFactory;
import com.readytalk.swt.widgets.buttons.SquareButton;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class CollaboratePresentExample implements SwtBlingExample {
  @RunnableExample(name = "CollaboratePresent")
  public CollaboratePresentExample() { }

  private Image presentImage;
  private Image collabImage;

  @Override
  public void run(Display display, Shell shell) {
    shell.setLayout(new FillLayout());
    Composite composite = new Composite(shell, SWT.NONE);

    GridLayout gridLayout = new GridLayout();
    gridLayout.makeColumnsEqualWidth = true;
    gridLayout.numColumns = 2;
    gridLayout.marginHeight = 10;
    gridLayout.marginWidth = 10;
    gridLayout.verticalSpacing = 20;
    gridLayout.horizontalSpacing = 20;
    composite.setLayout(gridLayout);

    Font headerFont = FontFactory.getFont(display, 30);

    Label header = new Label(composite, SWT.CENTER);
    header.setText("Select a Meeting Type");
    header.setFont(headerFont);
    header.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));

    loadImages(display);

    Font buttonFont = FontFactory.getFont(display, 20);
    SquareButton presentButton = new SquareButton.SquareButtonBuilder()
            .setHorizontallyCenterContents(true)
            .setVerticallyCenterContents(true)
            .setImage(presentImage)
            .setImagePosition(SquareButton.ImagePosition.ABOVE_TEXT)
            .setText("Present")
            .setFont(buttonFont)
            .setParent(composite)
            .build();
    presentButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));

    SquareButton collaborateButton = new SquareButton.SquareButtonBuilder()
            .setHorizontallyCenterContents(true)
            .setVerticallyCenterContents(true)
            .setImage(collabImage)
            .setImagePosition(SquareButton.ImagePosition.ABOVE_TEXT)
            .setText("Collaborate")
            .setFont(buttonFont)
            .setParent(composite)
            .build();
    collaborateButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));

    SquareButton startButton = new SquareButton.SquareButtonBuilder()
            .setHorizontallyCenterContents(true)
            .setVerticallyCenterContents(true)
            .setText("Start")
            .setFont(buttonFont)
            .setParent(composite)
            .build();
    startButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 2, 1));

    shell.setSize(800, 500);
    shell.open();
  }

  private void loadImages(Display display) {
    presentImage = new Image(display, "src/examples/resources/images/present.png");
    collabImage = new Image(display, "src/examples/resources/images/collaborate.png");
  }
}
