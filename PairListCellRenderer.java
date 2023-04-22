import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;

public class PairListCellRenderer extends JLabel implements ListCellRenderer<Pair<?>> {

	private static final long serialVersionUID = 1L;

	public PairListCellRenderer() {
		this.setOpaque(true);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Pair<?>> list, Pair<?> value, int index, boolean isSelected, boolean cellHasFocus) {
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
