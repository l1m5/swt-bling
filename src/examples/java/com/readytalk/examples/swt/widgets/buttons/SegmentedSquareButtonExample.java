package com.readytalk.examples.swt.widgets.buttons;

import com.readytalk.examples.swt.RunnableExample;
import com.readytalk.swt.widgets.buttons.SegmentedSquareButton;
import com.readytalk.swt.widgets.buttons.SquareButton;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 */
public class SegmentedSquareButtonExample {
  public static final Display DISPLAY = new Display();

  private static String LEFT_BUTTON_TEXT = "Left";
  private static String CENTER_BUTTON_TEXT = "Center";
  private static String RIGHT_BUTTON_TEXT = "Right";

  @RunnableExample(name="SquareButton")
  public static void run() {
    Shell shell = new Shell(DISPLAY);
    shell.setLayout(new FillLayout());
    Composite composite = new Composite(shell, SWT.NONE);
    composite.setLayout(new GridLayout());

    SegmentedSquareButton buttonGroup = new SegmentedSquareButton(shell, SWT.CENTER,
      new SegmentedSquareButton.SegmentedSquareButtonItem().setText(LEFT_BUTTON_TEXT),
      new SegmentedSquareButton.SegmentedSquareButtonItem().setText(CENTER_BUTTON_TEXT),
      new SegmentedSquareButton.SegmentedSquareButtonItem().setText(RIGHT_BUTTON_TEXT));

    shell.setSize(200, 200);
    shell.open();
    while (!shell.isDisposed()) {
      if (!DISPLAY.readAndDispatch()) {
        DISPLAY.sleep();
      }
    }

    DISPLAY.dispose();
  }
}
