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

package analyses.order.twopointsnp;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import analyses.FortranRunner;
import analyses.StreamCatcher;
import doe.MsgBox;
import gui.Prefs;

class RunTwoPointSNP extends FortranRunner {
	// How many lines have been processed?
	int count = 0;
	int maxCount = 100;
	// What are the names of the two markers being processed
	String marker1 = "", marker2 = "";

	private String charExclDup, charFullOut;

	// RunTwoPointSNP()
	RunTwoPointSNP(String charExclDup, String charFullOut) {
		this.charExclDup = charExclDup;
		this.charFullOut = charFullOut;
	}

	public void run() {
		try {
			proc = Runtime.getRuntime().exec(Prefs.tools_SNPtwopoint_path, null, 
					new File(Prefs.tools_scratch));

			PrintWriter writer = new PrintWriter(new OutputStreamWriter(proc.getOutputStream()));

			new TwoPointCatcher(proc.getInputStream());
			new TwoPointCatcher(proc.getErrorStream());

			writer.println("twopoint");
			writer.println("" + charFullOut);
			writer.println("" + charExclDup);
			writer.close();

			proc.waitFor();
		} catch (Exception e) {
			error = true;
			MsgBox.msg("TwoPoint was unable to run due to the following error:" + "\n" + e, MsgBox.ERR);
		}

		isRunning = false;
	}

	class TwoPointCatcher extends StreamCatcher {
		int i;

		TwoPointCatcher(InputStream in) {
			super(in);
		}

		public void processLine(String line) {
			if (line.startsWith("forrtl: severe")) {
				error = true;
				MsgBox.msg("Critical error:\n" + line, MsgBox.ERR);
			} else if (line.startsWith(" Remaining")) {
				String[] l = line.split("(\\s)+");
				i = Integer.parseInt(l[5]);
				maxCount = i * (i + 1) / 2;
			} else if (line.startsWith("pairwise")) {
				count += i;
				i -= 1;
			}
		}

	}
}