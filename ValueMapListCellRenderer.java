import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;

public class ValueMapListCellRenderer extends JLabel implements ListCellRenderer<ValueMap<?>> {

	private static final long serialVersionUID = 1L;

	public ValueMapListCellRenderer() {
		this.setOpaque(true);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends ValueMap<?>> list, ValueMap<?> value, int index, boolean isSelected, boolean cellHasFocus) {
		String name = value.getName();
		this.setText(name);
		this.setBorder(new EmptyBorder(1, 0, 1, 0));
		
		if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
		
		return this;
	}

}
