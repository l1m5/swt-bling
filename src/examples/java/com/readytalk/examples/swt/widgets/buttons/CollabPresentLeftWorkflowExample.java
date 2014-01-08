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

  private PaintListener presentInfoPaintListener;
  private PaintListener collabInfoPaintListener;

  private SquareButton presentButton;
  private Image presentImage;

  private SquareButton collabButton;
  private Image collabImage;

  private Composite presentationInfo;
  private Composite collabInfo;

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
    gridLayout.horizontalSpacing = 20;
    mainComposite.setLayout(gridLayout);

    Font headerFont = FontFactory.getFont(display, 30);

    Label header = new Label(mainComposite, SWT.CENTER);
    header.setText("Select a Meeting Type");
    header.setFont(headerFont);
    header.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 3, 1));

    loadImages(display);
    createLeftComposite(mainComposite);
    presentationInfo = createPresentExplanationComposite(mainComposite);
    collabInfo = createCollabExplanationComposite(mainComposite);

    shell.pack();
    shell.open();
  }

  private void loadImages(Display display) {
    presentImage = new Image(display, "src/examples/resources/images/glyph_present.png");
    collabImage = new Image(display, "src/examples/resources/images/glyph_collab.png");
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
        collabInfo.setVisible(false);
        presentationInfo.setVisible(true);
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
        presentationInfo.setVisible(false);
        collabInfo.setVisible(true);
      }
    });
    collabButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
  }

  private Composite createPresentExplanationComposite(Composite parentComposite) {
    Composite presentationInfoComposite = new Composite(parentComposite, SWT.NONE);
    String presentationExplanation = "'''Presentation Mode (Traditional)'''\n" +
            " - Participants will join using the Adobe Flash participant client ('''faster entry''' to conference)\n" +
            " - Great for a '''one-to-many''' presentation\n" +
            " - Participants can '''view screenshare''', but '''cannot share their screen''' ";

    presentationInfoComposite = wireTextPainterToComposite(presentationInfoComposite, presentationExplanation);

    return presentationInfoComposite;
  }

  private Composite createCollabExplanationComposite(Composite parentComposite) {
    Composite collabInfoComposite = new Composite(parentComposite, SWT.NONE);
    String collabExplanation = "'''Collaboration Mode (New!)'''\n" +
            " - All participants see the '''full-featured''' client\n" +
            " - Great for '''smaller''', collaborative meetings\n" +
            " - Participants can '''view screenshare''' and '''share their screen'''";

    collabInfoComposite = wireTextPainterToComposite(collabInfoComposite, collabExplanation);

    return collabInfoComposite;
  }

  private Composite wireTextPainterToComposite(final Composite composite, String text) {
    composite.setLayout(new GridLayout());
    composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

    final TextPainter textPainter = new TextPainter(composite)
            .setTokenizer(TextTokenizerFactory.createTextTokenizer(TextTokenizerType.FORMATTED))
            .setText(text);

    composite.addPaintListener(new PaintListener() {
      public void paintControl(PaintEvent e) {
        // TODO: improvement is desirable here
        Rectangle bounds = composite.getBounds();
        textPainter.setBounds(new Rectangle(0, 0, bounds.width, bounds.height));

        textPainter.handlePaint(e);
      }
    });

    composite.setVisible(false);

    return composite;
  }
}
