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
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class CollabPresentPptWorkflowExample implements SwtBlingExample {
  @RunnableExample(name = "CollabPresentPpt")
  public CollabPresentPptWorkflowExample() { }

  private static final int TEXT_LEFT_BORDER = 50;
  private static final int TEXT_PADDING_WIDTH_REDUCER = 100;
  private static final int SPACE_BETWEEN_PRESENT_COLLAB_BUTTONS = 10;
  private static final Font HEADER_FONT = FontFactory.getFont(Display.getDefault(), 30);
  private static final int MARGIN_BELOW_HEADER = 0;
  private static final int SPACING = 20;
  private static final Color INFO_COMPOSITE_COLOR = ColorFactory.getColor(200, 200, 200);

  private TextPainter presentInfoTextPainter;
  private TextPainter collabInfoTextPainter;
  private PaintListener presentInfoPaintListener;
  private PaintListener collabInfoPaintListener;

  private SquareButton currentButtonSelected;

  private SquareButton presentButton;
  private Image presentImage;

  private SquareButton collabButton;
  private Image collabImage;
  private Image collabImageLarge;
  private Image presentImageLarge;

  private Composite headerComposite;
  private Composite infoComposite;

  private Composite meetingTypeComposite;
  private Composite textPainterComposite;
  private Label infoImage;

  @Override
  public void run(Display display, Shell shell) {
    shell.setLayout(new FillLayout());

    Composite mainComposite = createMainComposite(shell);

    loadImages(display);

    createHeader(mainComposite);

    Font buttonFont = FontFactory.getFont(16, SWT.NORMAL);
    createMeetingTypeComposite(mainComposite, buttonFont);
    createInfoComposite(mainComposite);
    togglePresentInfo();

    createStartCancelComposite(infoComposite, buttonFont);

    shell.pack();
    shell.open();
  }

  private Composite createMainComposite(Shell shell) {
    Composite mainComposite = new Composite(shell, SWT.NONE);

    FormLayout formLayout = new FormLayout();
    formLayout.marginLeft = 20;
    formLayout.marginTop = 10;

    mainComposite.setLayout(formLayout);
    return mainComposite;
  }

  private void createHeader(Composite parentComposite) {
    headerComposite = new Composite(parentComposite, SWT.NONE);

    FormData formData = new FormData();
    formData.top = new FormAttachment(0);
    formData.left = new FormAttachment(0);
    formData.right = new FormAttachment(100);
    headerComposite.setLayoutData(formData);

    GridLayout gridLayout = new GridLayout();
    gridLayout.marginBottom = MARGIN_BELOW_HEADER;
    headerComposite.setLayout(gridLayout);

    Label header = new Label(headerComposite, SWT.NONE);
    header.setText("Select a Meeting Type");
    header.setFont(HEADER_FONT);
    header.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, false));
  }

  private void createMeetingTypeComposite(Composite parentComposite, Font buttonFont) {
    meetingTypeComposite = new Composite(parentComposite, SWT.NONE);

    GridLayout gridLayout = new GridLayout(1, false);
    gridLayout.marginHeight = 0;
    gridLayout.marginWidth = 0;
    gridLayout.verticalSpacing = SPACE_BETWEEN_PRESENT_COLLAB_BUTTONS;
    meetingTypeComposite.setLayout(gridLayout);

    FormData formData = new FormData();
    formData.top = new FormAttachment(headerComposite, SPACING);
    formData.left = new FormAttachment(0);
    meetingTypeComposite.setLayoutData(formData);

    GridData data = new GridData(SWT.FILL, SWT.FILL, true, false);
    data.minimumWidth = 200;
    presentButton = new SquareButton.SquareButtonBuilder()
            .setParent(meetingTypeComposite)
            .setImage(presentImage)
            .setImagePosition(SquareButton.ImagePosition.LEFT_OF_TEXT)
            .setHorizontallyCenterContents(false)
            .setToggleable(true)
            .setText("Present")
            .setFont(buttonFont)
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
            .setParent(meetingTypeComposite)
            .setImage(collabImage)
            .setImagePosition(SquareButton.ImagePosition.LEFT_OF_TEXT)
            .setHorizontallyCenterContents(false)
            .setToggleable(true)
            .setText("Collaborate")
            .setFont(buttonFont)
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

  private void createInfoComposite(Composite parentComposite) {
    infoComposite = new Composite(parentComposite, SWT.BORDER);

    FormData formData = new FormData();
    formData.top = new FormAttachment(headerComposite, SPACING);
    formData.left = new FormAttachment(meetingTypeComposite);
    formData.width = 500;
    infoComposite.setLayoutData(formData);

    infoComposite.setLayout(new FormLayout());
    infoComposite.setBackground(INFO_COMPOSITE_COLOR);

    Composite infoImageComposite = new Composite(infoComposite, SWT.NONE);
    infoImageComposite.setBackground(INFO_COMPOSITE_COLOR);
    formData = new FormData();
    formData.top = new FormAttachment(0);
    formData.left = new FormAttachment(0);
    formData.right = new FormAttachment(100);
    infoImageComposite.setLayoutData(formData);
    infoImageComposite.setLayout(new GridLayout());
    infoImage = new Label(infoImageComposite, SWT.NONE);

    GridData gridData = new GridData(SWT.CENTER, SWT.FILL, true, false);
    infoImage.setLayoutData(gridData);

    textPainterComposite = new Composite(infoComposite, SWT.NONE);
    textPainterComposite.setBackground(INFO_COMPOSITE_COLOR);
    textPainterComposite.setLayout(new FillLayout());
    formData = new FormData();
    formData.top = new FormAttachment(infoImageComposite, 10);
    formData.left = new FormAttachment(0);
    formData.right = new FormAttachment(100);
    formData.height = 70;
    textPainterComposite.setLayoutData(formData);

    presentInfoTextPainter = createPresentInfoTextPainter(textPainterComposite);
    collabInfoTextPainter = createCollabInfoTextPainter(textPainterComposite);
  }

  private void loadImages(Display display) {
    presentImage = new Image(display, "src/examples/resources/images/glyph_present.png");
    collabImage = new Image(display, "src/examples/resources/images/glyph_collab.png");
    collabImageLarge = new Image(display, "src/examples/resources/images/glyph_collab-large.png");
    presentImageLarge = new Image(display, "src/examples/resources/images/glyph_present-large.png");
  }

  private void setPresentInfoVisible(boolean isVisible) {
    if (presentInfoPaintListener == null) {
      presentInfoPaintListener = new PaintListener() {
        public void paintControl(PaintEvent e) {
          Rectangle bounds = textPainterComposite.getBounds();
          presentInfoTextPainter.setBounds(new Rectangle(TEXT_LEFT_BORDER, 0, bounds.width - TEXT_PADDING_WIDTH_REDUCER, bounds.height));

          presentInfoTextPainter.handlePaint(e);
        }
      };
    }

    if (isVisible) {
      setCollabInfoVisible(false);
      infoImage.setImage(presentImageLarge);
      textPainterComposite.addPaintListener(presentInfoPaintListener);
    } else {
      textPainterComposite.removePaintListener(presentInfoPaintListener);
    }

    textPainterComposite.redraw();
  }

  private void setCollabInfoVisible(boolean isVisible) {
    if (collabInfoPaintListener == null) {
      collabInfoPaintListener = new PaintListener() {
        public void paintControl(PaintEvent e) {
          Rectangle bounds = textPainterComposite.getBounds();

          collabInfoTextPainter.setBounds(new Rectangle(TEXT_LEFT_BORDER, 0, bounds.width - TEXT_PADDING_WIDTH_REDUCER, bounds.height));

          collabInfoTextPainter.handlePaint(e);
        }
      };
    }

    if (isVisible) {
      setPresentInfoVisible(false);
      infoImage.setImage(collabImageLarge);
      textPainterComposite.addPaintListener(collabInfoPaintListener);
    } else {
      textPainterComposite.removePaintListener(collabInfoPaintListener);
    }

    textPainterComposite.redraw();
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

  private TextPainter createPresentInfoTextPainter(Composite parentComposite) {
//    String presentationExplanation = "'''Presentation Mode (Traditional)'''\n" +
//            " - Participants will join using the Adobe Flash client\n" +
//            " - Great for a '''one-to-many''' presentation\n" +
//            " - Participants can '''view screenshare''', but '''cannot share their screen''' ";
    String presentationExplanation = "Lorem ipsum dolor sit amet, consectetur adipisicing elit, " +
            "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";

    return createTextPainterForString(parentComposite, presentationExplanation, SWT.CENTER);
  }

  private TextPainter createCollabInfoTextPainter(Composite parentComposite) {
//    String collabExplanation = "'''Collaboration Mode (New!)'''\n" +
//            " - All participants see the '''full-featured''' client\n" +
//            " - Great for '''smaller''', collaborative meetings\n" +
//            " - Participants can '''view screenshare''' and '''share their screen'''";
    String collabExplanation = "Lorem ipsum dolor sit amet!\n \n" +
              " - sed do eiusmod tempor incididunt ut labore\n" +
              " - ut labore et dolore magna aliqua\n" +
              " - consectetur adipisicing elit";

    return createTextPainterForString(parentComposite, collabExplanation, SWT.LEFT);
  }

  private TextPainter createTextPainterForString(final Composite composite, String text, int justification) {
    return new TextPainter(composite)
            .setTokenizer(TextTokenizerFactory.createTextTokenizer(TextTokenizerType.FORMATTED))
            .setJustification(justification)
            .setText(text);
  }

  private void createStartCancelComposite(final Composite parentComposite, Font buttonFont) {
    // TODO: If we were to use this, we should use a CustomActionCancelButton builder here (to flip the buttons per OS)
    Composite startCancelComposite = new Composite(parentComposite, SWT.NONE);
    startCancelComposite.setBackground(INFO_COMPOSITE_COLOR);

    FormData formData = new FormData();
    formData.top = new FormAttachment(textPainterComposite);
    formData.left = new FormAttachment(0);
    formData.right = new FormAttachment(100);
    formData.bottom = new FormAttachment(100);
    startCancelComposite.setLayoutData(formData);

    GridLayout gridLayout = new GridLayout(2, false);
    gridLayout.horizontalSpacing = 10;
    startCancelComposite.setLayout(gridLayout);

    SquareButton cancelButton = new SquareButton.SquareButtonBuilder()
            .setParent(startCancelComposite)
            .setText("Cancel")
            .build();
    cancelButton.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {

      }
    });

//    BootStrap Primary Button Color Scheme
//    SquareButton.SquareButtonColorGroup startButtonColorGroup = new SquareButton.SquareButtonColorGroup(
//            ColorFactory.getColor(66, 139, 202),
//            ColorFactory.getColor(66, 139, 202),
//            ColorFactory.getColor(53, 126, 189),
//            ColorFactory.getColor(255, 255, 255)
//    );

//    BootStrap Success Button Color Scheme
    SquareButton.SquareButtonColorGroup defaultStartButtonColors = new SquareButton.SquareButtonColorGroup(
            ColorFactory.getColor(92, 184, 92),
            ColorFactory.getColor(92, 184, 92),
            ColorFactory.getColor(76, 174, 76),
            ColorFactory.getColor(255, 255, 255)
    );

    SquareButton.SquareButtonColorGroup hoverStartButtonColors = new SquareButton.SquareButtonColorGroup(
            ColorFactory.getColor(71, 164, 71),
            ColorFactory.getColor(71, 164, 71),
            ColorFactory.getColor(57, 132, 57),
            ColorFactory.getColor(255, 255, 255)
    );

    SquareButton startButton = new SquareButton.SquareButtonBuilder()
            .setParent(startCancelComposite)
            .setText("Start")
            .setFont(buttonFont)
            .setDefaultColors(defaultStartButtonColors)
            .setSelectedColors(defaultStartButtonColors)
            .setHoverColors(hoverStartButtonColors)
            .build();
    startButton.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {

      }
    });


    cancelButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false));
    GridData gridData = new GridData(SWT.LEFT, SWT.CENTER, true, false);
    gridData.minimumWidth = 125;
    startButton.setLayoutData(gridData);
  }
}
