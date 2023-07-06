package View;
import java.awt.Toolkit;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

/**
 * Parent class of <code>IntFilter</code> and <code>UIntFilter</code>
 * Both of these classes differ only by the algorithm used to check if a value is within the filter, so shared methods are put here
 * @author ender
 */
public abstract class TypeFilter extends DocumentFilter {

	@Override
	public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
		Document doc = fb.getDocument();
		StringBuilder sb = new StringBuilder();
		sb.append(doc.getText(0, doc.getLength()));
		sb.insert(offset, string);
		
		// test if sb.toString() is a valid int; if so, call super.insert()
		if (isType(sb.toString())) super.insertString(fb, offset, string, attr);
		else Toolkit.getDefaultToolkit().beep();
	}
	
	@Override
	public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException {
		Document doc = fb.getDocument();
		StringBuilder sb = new StringBuilder();
		sb.append(doc.getText(0, doc.getLength()));
		sb.delete(offset, offset + length);
		
		// test if sb.toString() is a valid int; if so, call super.delete()
		if (isType(sb.toString())) super.remove(fb, offset, length);
		else Toolkit.getDefaultToolkit().beep();
	}
	
	@Override
	public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
		Document doc = fb.getDocument();
		StringBuilder sb = new StringBuilder();
		sb.append(doc.getText(0, doc.getLength()));
		sb.replace(offset, offset + length, text);
		
		// test if sb.toString() is a valid int; if so, call super.replace()
		if (isType(sb.toString())) super.replace(fb, offset, length, text, attrs);
		else Toolkit.getDefaultToolkit().beep();
	}
	
	/**
	 * Determines if the contents of the <code>JComponent</code> fit within the filter
	 * @param string the text in the <code>JComponent</code>
	 * @return true if the text fits within the filter; otherwise, false
	 */
	public abstract boolean isType(String string);
	
}
