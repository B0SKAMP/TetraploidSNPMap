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

package analyses.fastcluster;

import data.Cluster;
import data.LinkageGroup;
import gui.AppFrame;
import doe.MsgBox;

public class SNPClusterAnalysis {
	private boolean isOK = false;
	
	private Cluster cluster = new Cluster();
	
	/** SNP only. Opens the ClusterDialog. CLusterDialog runs Chimatrix (fortran) and fastcluster (R).
	 * 
	 */
	public SNPClusterAnalysis(AppFrame frame, LinkageGroup grp) {
		ClusterDialog dialog = new ClusterDialog(frame, grp, cluster);
				
		if (dialog.isOK() && cluster.getGroups().size() > 0) {
			isOK = true;
		} else if (dialog.isOK() 
				&& cluster.getGroups().size() == 0) {
			MsgBox.msg("No linkage groups were returned by the analysis.",
				MsgBox.INF);
		}
	}
	
	public Cluster getCluster() { 
		return isOK ? cluster : null;
	}
}
