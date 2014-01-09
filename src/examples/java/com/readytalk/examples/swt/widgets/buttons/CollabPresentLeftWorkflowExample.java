package com.readytalk.examples.swt.widgets.buttons;

import com.readytalk.examples.swt.RunnableExample;
import com.readytalk.examples.swt.SwtBlingExample;
import com.readytalk.swt.text.painter.TextPainter;
import com.readytalk.swt.text.tokenizer.TextTokenizerFactory;
import com.readytalk.swt.text.tokenizer.TextTokenizerType;
import com.readytalk.swt.util.FontFactory;
import com.readytalk.swt.widgets.buttons.SquareButton;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class CollabPresentLeftWorkflowExample implements SwtBlingExample {
  @RunnableExample(name = "CollabPresentLeft")
  public CollabPresentLeftWorkflowExample() { }

  private TextPainter presentInfoTextPainter;
  private TextPainter collabInfoTextPainter;
  private PaintListener presentInfoPaintListener;
  private PaintListener collabInfoPaintListener;

  private SquareButton presentButton;
  private Image presentImage;

  private SquareButton collabButton;
  private Image collabImage;

  private Composite middleComposite;

  @Override
  public void run(Display display, Shell shell) {
    shell.setLayout(new FillLayout());
    Composite mainComposite = new Composite(shell, SWT.NONE);

    GridLayout gridLayout = new GridLayout();
    gridLayout.makeColumnsEqualWidth = false;
    gridLayout.numColumns = 3;
    gridLayout.marginHeight = 10;
    gridLayout.marginWidth = 10;
    gridLayout.verticalSpacing = 20;
    gridLayout.horizontalSpacing = 30;
    mainComposite.setLayout(gridLayout);

    Font headerFont = FontFactory.getFont(display, 30);

    Label header = new Label(mainComposite, SWT.CENTER);
    header.setText("Select a Meeting Type");
    header.setFont(headerFont);
    header.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 3, 1));

    loadImages(display);
    createLeftComposite(mainComposite);

    middleComposite = createMiddleComposite(mainComposite);
    presentInfoTextPainter = createPresentInfoTextPainter(middleComposite);
    collabInfoTextPainter = createCollabInfoTextPainter(middleComposite);
    setPresentInfoVisible(true);

    createRightComposite(mainComposite);

    shell.pack();
    shell.open();
  }

  private void loadImages(Display display) {
    presentImage = new Image(display, "src/examples/resources/images/glyph_present.png");
    collabImage = new Image(display, "src/examples/resources/images/glyph_collab.png");
  }

  private void setPresentInfoVisible(boolean isVisible) {
    if (presentInfoPaintListener == null) {
      presentInfoPaintListener = new PaintListener() {
        public void paintControl(PaintEvent e) {
          Rectangle bounds = middleComposite.getBounds();
          presentInfoTextPainter.setBounds(new Rectangle(0, 0, bounds.width, bounds.height));

          presentInfoTextPainter.handlePaint(e);
        }
      };
    }

    if (isVisible) {
      setCollabInfoVisible(false);
      middleComposite.addPaintListener(presentInfoPaintListener);
      middleComposite.redraw();
    } else {
      middleComposite.removePaintListener(presentInfoPaintListener);
    }
  }

  private void setCollabInfoVisible(boolean isVisible) {
    if (collabInfoPaintListener == null) {
      collabInfoPaintListener = new PaintListener() {
        public void paintControl(PaintEvent e) {
          Rectangle bounds = middleComposite.getBounds();

          collabInfoTextPainter.setBounds(new Rectangle(0, 0, bounds.width, bounds.height));

          collabInfoTextPainter.handlePaint(e);
        }
      };
    }

    if (isVisible) {
      setPresentInfoVisible(false);
      middleComposite.addPaintListener(collabInfoPaintListener);
      middleComposite.redraw();
    } else {
      middleComposite.removePaintListener(collabInfoPaintListener);
    }
  }

  private void createLeftComposite(Composite parentComposite) {
    Composite leftComposite = new Composite(parentComposite, SWT.NONE);
    GridLayout gridLayout = new GridLayout();
    gridLayout.marginHeight = 100;
    leftComposite.setLayout(gridLayout);

    presentButton = new SquareButton.SquareButtonBuilder()
            .setParent(leftComposite)
            .setImage(presentImage)
            .setImagePosition(SquareButton.ImagePosition.LEFT_OF_TEXT)
            .setHorizontallyCenterContents(false)
            .setText("Present")
            .build();
    presentButton.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent e) {
        setPresentInfoVisible(true);
      }
    });
    presentButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

    collabButton = new SquareButton.SquareButtonBuilder()
            .setParent(leftComposite)
            .setImage(collabImage)
            .setImagePosition(SquareButton.ImagePosition.LEFT_OF_TEXT)
            .setHorizontallyCenterContents(false)
            .setText("Collaborate")
            .build();
    collabButton.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent e) {
        setCollabInfoVisible(true);
      }
    });
    collabButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
  }

  private Composite createMiddleComposite(Composite parentComposite) {
    Composite middleComposite = new Composite(parentComposite, SWT.NONE);
    middleComposite.setLayout(new GridLayout());
    GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
    data.minimumWidth = 500;
    middleComposite.setLayoutData(data);

    return middleComposite;
  }

  private TextPainter createPresentInfoTextPainter(Composite parentComposite) {
    String presentationExplanation = "'''Presentation Mode (Traditional)'''\n" +
            " - Participants will join using the Adobe Flash client\n" +
            " - Great for a '''one-to-many''' presentation\n" +
            " - Participants can '''view screenshare''', but '''cannot share their screen''' ";

    return createTextPainterForString(parentComposite, presentationExplanation);
  }

  private TextPainter createCollabInfoTextPainter(Composite parentComposite) {
    String collabExplanation = "'''Collaboration Mode (New!)'''\n" +
            " - All participants see the '''full-featured''' client\n" +
            " - Great for '''smaller''', collaborative meetings\n" +
            " - Participants can '''view screenshare''' and '''share their screen'''";

    return createTextPainterForString(parentComposite, collabExplanation);
  }

  private TextPainter createTextPainterForString(final Composite composite, String text) {
    return new TextPainter(composite)
            .setTokenizer(TextTokenizerFactory.createTextTokenizer(TextTokenizerType.FORMATTED))
            .setText(text);
  }

  private void createRightComposite(final Composite parentComposite) {
    Composite rightComposite = new Composite(parentComposite, SWT.NONE);
    rightComposite.setLayout(new GridLayout());
    rightComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));

    SquareButton startButton = new SquareButton.SquareButtonBuilder()
            .setParent(rightComposite)
            .setText("Start")
            .build();
    startButton.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
//        parentComposite.getShell().close();
      }
    });
  }
}
