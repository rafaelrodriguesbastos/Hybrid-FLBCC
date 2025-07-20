package util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DaoMembershipDegreesOfType2FuzzySets {

	private MembershipDegreesOfType2FuzzySets membershipDegreesOfType2FuzzySets;

	private ArrayList<MembershipDegreesOfType2FuzzySets> list = new ArrayList<>();

	String pathFile = "/home/bruno/tmp/";
	String fileName = "juzzy_output.csv";
	String fileHeader = "Label, inCP, inCC, inRam, xPonctual, xInf, xSup,  LowULower, LowUUpper, AverageULower, AverageUUpper, HighULower, HighUUpper";
	private LibFile libFile = new LibFile();

	public void adicionar(MembershipDegreesOfType2FuzzySets membershipDegreesOfType2FuzzySets) {

		try {
			adicionarCabecalho();
		} catch (IOException e) {
			e.printStackTrace();
		}

		ExperimetName experimetNamePublico = new ExperimetName();

		// JOptionPane.showMessageDialog(null,
		// experimetNamePublico.getEXPERIMENT_NAME());

		// if (!list.contains(membershipDegreesOfType2FuzzySets)) {
		list.add(membershipDegreesOfType2FuzzySets);

		String linha = experimetNamePublico.getExperimentName() + ","   
		        + membershipDegreesOfType2FuzzySets.getInCP()+ "," 
				+ membershipDegreesOfType2FuzzySets.getInCC() + "," 
		        + membershipDegreesOfType2FuzzySets.getInRam()+ "," 
				+ membershipDegreesOfType2FuzzySets.getxPonctual() + ","
				+ membershipDegreesOfType2FuzzySets.getxInf() + ","
				+ membershipDegreesOfType2FuzzySets.getxSup() + ","
				+ membershipDegreesOfType2FuzzySets.getLowUtilizationDegreeOfMembershipLowerBound() + ","
				+ membershipDegreesOfType2FuzzySets.getLowUtilizationDegreeOfMembershipUpperBound() + ","
				+ membershipDegreesOfType2FuzzySets.getAverageUtilizationDegreeOfMembershipLowerBound() + ","
				+ membershipDegreesOfType2FuzzySets.getAverageUtilizationDegreeOfMembershipUpperBound() + ","
				+ membershipDegreesOfType2FuzzySets.getHighUtilizationDegreeOfMembershipLowerBound() + ","
				+ membershipDegreesOfType2FuzzySets.getHighUtilizationDegreeOfMembershipUpperBound();

		libFile.adicionarLinhaTxt(linha, this.pathFile, this.fileName);

		/*
		 * System.out.println(membershipDegreesOfType2FuzzySets.
		 * getLowUtilizationDegreeOfMembershipLowerBound()+" - "+
		 * membershipDegreesOfType2FuzzySets.
		 * getHighUtilizationDegreeOfMembershipUpperBound());
		 */
		// }

	}

	public DaoMembershipDegreesOfType2FuzzySets() {
		super();

		// libFile.adicionarLinhaTxt(this.fileHeader, this.pathFile, this.fileName);
	}

	public void adicionarCabecalho() throws IOException {

		String fullFile = this.pathFile + this.fileName;
		File arquivoLeitura = new File(fullFile);

		if (!arquivoLeitura.exists()) {

			// https://www.guj.com.br/t/quantidade-de-linhas-de-um-arquivo/45691/5

			// LineNumberReader linhaLeitura = new LineNumberReader(new
			// FileReader(arquivoLeitura));
			// linhaLeitura.skip(arquivoLeitura.length());

			// if(linhaLeitura.getLineNumber() == 0)
			libFile.adicionarLinhaTxt(this.fileHeader, this.pathFile, this.fileName);
		}

	}

	public MembershipDegreesOfType2FuzzySets getMembershipDegreesOfType2FuzzySets() {
		return membershipDegreesOfType2FuzzySets;
	}

	public void setMembershipDegreesOfType2FuzzySets(
			MembershipDegreesOfType2FuzzySets membershipDegreesOfType2FuzzySets) {
		this.membershipDegreesOfType2FuzzySets = membershipDegreesOfType2FuzzySets;
	}

	public ArrayList<MembershipDegreesOfType2FuzzySets> getList() {
		return list;
	}

	public void setList(ArrayList<MembershipDegreesOfType2FuzzySets> list) {
		this.list = list;
	}

}
