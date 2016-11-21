/* Copyright (c) 2005-2016 Biomathematics and Statistics Scotland
 * http://www.bioss.ac.uk/ 
 * 
 * This file is part of TetraploidMap.
 *
 *    TetraploidMap is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    TetraploidMap is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with TetraploidMap.  If not, see <http://www.gnu.org/licenses/>.
 */

package gui.nav;

import data.Cluster;
import gui.AppFrame;
import gui.DendrogramsPanel;
import gui.IPrintable;

/*
 * TV: This class is called just after clustering has been completed and the dendrogram has
 * been constructed. This class makes the dendrogram visible on the GUI through the NavPanel.
 * 
 * Depending on the running mode (SNP or other) different DendrogramsPanel constructor is called.
 */
class DendrogramsNode implements IPrintable {
	DendrogramsPanel panel;

	DendrogramsNode(Cluster cluster) {
		panel = new DendrogramsPanel(cluster, AppFrame.tpmmode);
	}

	public String toString() {
		return "Static dendrogram";
	}

	public boolean isPrintable() {
		return true;
	}

	public void print() {
		panel.print();
	}
}