package com.readytalk.examples.swt.widgets.buttons;

import com.readytalk.examples.swt.RunnableExample;
import com.readytalk.examples.swt.SwtBlingExample;
import com.readytalk.swt.text.painter.TextPainter;
import com.readytalk.swt.text.tokenizer.TextTokenizerFactory;
import com.readytalk.swt.text.tokenizer.TextTokenizerType;
import com.readytalk.swt.util.ColorFactory;
import com.readytalk.swt.util.FontFactory;
import com.readytalk.swt.widgets.buttons.SquareButton;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
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

  private SquareButton currentButtonSelected;

  private SquareButton presentButton;
  private Image presentImage;

  private SquareButton collabButton;
  private Image collabImage;

  private Composite descriptionComposite;

  @Override
  public void run(Display display, Shell shell) {
    shell.setLayout(new FillLayout());
    Composite mainComposite = new Composite(shell, SWT.NONE);

    GridLayout gridLayout = new GridLayout();
    gridLayout.makeColumnsEqualWidth = false;
    gridLayout.numColumns = 2;
    gridLayout.marginHeight = 10;
    gridLayout.marginWidth = 10;
    gridLayout.verticalSpacing = 20;
    gridLayout.horizontalSpacing = 30;
    mainComposite.setLayout(gridLayout);

    Font headerFont = FontFactory.getFont(display, 30);

    Label header = new Label(mainComposite, SWT.CENTER);
    header.setText("Select a Meeting Type");
    header.setFont(headerFont);
    header.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));

    loadImages(display);
    createMeetingTypeComposite(mainComposite);

    descriptionComposite = createMiddleComposite(mainComposite);
    presentInfoTextPainter = createPresentInfoTextPainter(descriptionComposite);
    collabInfoTextPainter = createCollabInfoTextPainter(descriptionComposite);

    togglePresentInfo();

    createStartComposite(mainComposite);

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
          Rectangle bounds = descriptionComposite.getBounds();
          presentInfoTextPainter.setBounds(new Rectangle(0, 0, bounds.width, bounds.height));

          presentInfoTextPainter.handlePaint(e);
        }
      };
    }

    if (isVisible) {
      setCollabInfoVisible(false);
      descriptionComposite.addPaintListener(presentInfoPaintListener);
    } else {
      descriptionComposite.removePaintListener(presentInfoPaintListener);
    }

    descriptionComposite.redraw();
  }

  private void setCollabInfoVisible(boolean isVisible) {
    if (collabInfoPaintListener == null) {
      collabInfoPaintListener = new PaintListener() {
        public void paintControl(PaintEvent e) {
          Rectangle bounds = descriptionComposite.getBounds();

          collabInfoTextPainter.setBounds(new Rectangle(0, 0, bounds.width, bounds.height));

          collabInfoTextPainter.handlePaint(e);
        }
      };
    }

    if (isVisible) {
      setPresentInfoVisible(false);
      descriptionComposite.addPaintListener(collabInfoPaintListener);
    } else {
      descriptionComposite.removePaintListener(collabInfoPaintListener);
    }

    descriptionComposite.redraw();
  }

  private void createMeetingTypeComposite(Composite parentComposite) {
    Composite leftComposite = new Composite(parentComposite, SWT.NONE);
    GridLayout gridLayout = new GridLayout();
    gridLayout.marginHeight = 100;
    gridLayout.verticalSpacing = 50;
    leftComposite.setLayout(gridLayout);


    GridData data = new GridData(SWT.FILL, SWT.FILL, true, false);
    data.minimumWidth = 200;
    presentButton = new SquareButton.SquareButtonBuilder()
            .setParent(leftComposite)
            .setImage(presentImage)
            .setImagePosition(SquareButton.ImagePosition.LEFT_OF_TEXT)
            .setHorizontallyCenterContents(false)
            .setToggleable(true)
            .setText("Present")
            .build();
    presentButton.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent e) {
        if (presentButton.isToggled() && currentButtonSelected != presentButton) {
          togglePresentInfo();
        }
      }
    });
    presentButton.setLayoutData(data);

    collabButton = new SquareButton.SquareButtonBuilder()
            .setParent(leftComposite)
            .setImage(collabImage)
            .setImagePosition(SquareButton.ImagePosition.LEFT_OF_TEXT)
            .setHorizontallyCenterContents(false)
            .setToggleable(true)
            .setText("Collaborate")
            .build();
    collabButton.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent e) {
        if (collabButton.isToggled() && currentButtonSelected != collabButton) {
          toggleCollabInfo();
        }
      }
    });
    collabButton.setLayoutData(data);
  }

  private void togglePresentInfo() {
    currentButtonSelected = presentButton;
    collabButton.setToggled(false);
    presentButton.setToggled(true);
    setPresentInfoVisible(true);
  }

  private void toggleCollabInfo() {
    currentButtonSelected = collabButton;
    presentButton.setToggled(false);
    collabButton.setToggled(true);
    setCollabInfoVisible(true);
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

  private void createStartComposite(final Composite parentComposite) {
    // TODO: If we were to use this, we should use a CustomActionCancelButton builder here (to flip the buttons per OS)

    Composite rightComposite = new Composite(parentComposite, SWT.NONE);
    rightComposite.setLayout(new GridLayout());
    rightComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

//    BootStrap Primary Button Color Scheme
//    SquareButton.SquareButtonColorGroup startButtonColorGroup = new SquareButton.SquareButtonColorGroup(
//            ColorFactory.getColor(66, 139, 202),
//            ColorFactory.getColor(66, 139, 202),
//            ColorFactory.getColor(53, 126, 189),
//            ColorFactory.getColor(255, 255, 255)
//    );

//    BootStrap Success Button Color Scheme
    SquareButton.SquareButtonColorGroup startButtonColorGroup = new SquareButton.SquareButtonColorGroup(
            ColorFactory.getColor(92, 184, 92),
            ColorFactory.getColor(92, 184, 92),
            ColorFactory.getColor(76, 174, 76),
            ColorFactory.getColor(255, 255, 255)
    );

    SquareButton startButton = new SquareButton.SquareButtonBuilder()
            .setParent(rightComposite)
            .setText("Start")
            .setDefaultColors(startButtonColorGroup)
            .build();
    startButton.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
//        parentComposite.getShell().close();
      }
    });

    GridData data = new GridData(SWT.RIGHT, SWT.FILL, true, false);
    data.minimumWidth = 125;
    startButton.setLayoutData(data);
  }
}