package com.readytalk.swt.widgets.buttons;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.Assert;
import org.junit.Test;

/**
 */
public class SegmentedSquareButtonIntegTest {

  private String leftButtonText = "Left";
  private String centerButtonText = "Center";
  private String rightButtonText = "Right";


  @Test
  public void testTextSetting() {
    Display display = Display.getDefault();
    Shell shell = new Shell(display);

    shell.open();

    SegmentedSquareButton buttonGroup = new SegmentedSquareButton(shell, SWT.CENTER,
     new SegmentedSquareButton.SegmentedSquareButtonItem().setText(leftButtonText),
     new SegmentedSquareButton.SegmentedSquareButtonItem().setText(centerButtonText),
     new SegmentedSquareButton.SegmentedSquareButtonItem().setText(rightButtonText));

    shell.close();
    display.dispose();


  }

}
