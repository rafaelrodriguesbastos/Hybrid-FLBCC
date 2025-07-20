package org.cloudbus.cloudsim.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DaoMembershipDegreesOfType2FuzzySets {

	private MembershipDegreesOfType2FuzzySets membershipDegreesOfType2FuzzySets;

	private ArrayList<MembershipDegreesOfType2FuzzySets> list = new ArrayList<>();

	ExperimentName experimentNamePublico = new ExperimentName();

	String pathFile = "/home/bastos/Dados/temp/entropia/";
    String fileName = experimentNamePublico.getExperimentName()+".csv"; // = "entropia.csv";
    String fileHeader = "LowCpuLower, LowCpuUpper, MediumCpuLower, MediumCpuUpper, HighCpuLower, HighCpuUpper, LowRamLower, " +
			"LowRamUpper, MediumRamLower, MediumRamUpper, HighRamLower, HighRamUpper, LowBwLower, LowBwUpper, MediumBwLower, MediumBwUpper, " +
			"HighBwLower, HighBwUpper, LowMipsLower, LowMipsUpper, MediumMipsLower, MediumMipsUpper, HighMipsLower, HighMipsUpper, " +
			"LowStorageLower, LowStorageUpper, MediumStorageLower, MediumStorageUpper, HighStorageLower, HighStorageUpper, LowEnergyLower, " +
			"LowEnergyUpper, MediumEnergyLower, MediumEnergyUpper, HighEnergyLower, HighEnergy, UpperUnderUtilizationLower, " +
			"UnderUtilizationUpper, NormalUtilizationLower, NormalUtilizationUpper, OverUtilizationLower, OverUtilizationUpper";
    private LibFile libFile = new LibFile();

	public void adicionar(MembershipDegreesOfType2FuzzySets membershipDegreesOfType2FuzzySets)  {
		
		
		try {
			adicionarCabecalho();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
//        ExperimentName experimentNamePublico = new ExperimentName();
        
       // JOptionPane.showMessageDialog(null, experimetNamePublico.getEXPERIMENT_NAME());
        
		//if (!list.contains(membershipDegreesOfType2FuzzySets)) { 
			list.add(membershipDegreesOfType2FuzzySets);
			
			String linha =
					membershipDegreesOfType2FuzzySets.getLowCpuDegreeOfMembershipLowerBound() + "," +
					membershipDegreesOfType2FuzzySets.getLowCpuDegreeOfMembershipUpperBound() + "," +
					membershipDegreesOfType2FuzzySets.getMediumCpuDegreeOfMembershipLowerBound() + "," +
					membershipDegreesOfType2FuzzySets.getMediumCpuDegreeOfMembershipUpperBound() + "," +
					membershipDegreesOfType2FuzzySets.getHighCpuDegreeOfMembershipLowerBound() + "," +
					membershipDegreesOfType2FuzzySets.getHighCpuDegreeOfMembershipUpperBound() + "," +
					membershipDegreesOfType2FuzzySets.getLowRamDegreeOfMembershipLowerBound() + "," +
					membershipDegreesOfType2FuzzySets.getLowRamDegreeOfMembershipUpperBound() + "," +
					membershipDegreesOfType2FuzzySets.getMediumRamDegreeOfMembershipLowerBound() + "," +
					membershipDegreesOfType2FuzzySets.getMediumRamDegreeOfMembershipUpperBound() + "," +
					membershipDegreesOfType2FuzzySets.getHighRamDegreeOfMembershipLowerBound() + "," +
					membershipDegreesOfType2FuzzySets.getHighRamDegreeOfMembershipUpperBound() + "," +
					membershipDegreesOfType2FuzzySets.getLowBwDegreeOfMembershipLowerBound() + "," +
					membershipDegreesOfType2FuzzySets.getLowBwDegreeOfMembershipUpperBound() + "," +
					membershipDegreesOfType2FuzzySets.getMediumBwDegreeOfMembershipLowerBound() + "," +
					membershipDegreesOfType2FuzzySets.getMediumBwDegreeOfMembershipUpperBound() + "," +
					membershipDegreesOfType2FuzzySets.getHighBwDegreeOfMembershipLowerBound() + "," +
					membershipDegreesOfType2FuzzySets.getHighBwDegreeOfMembershipUpperBound() + "," +
					membershipDegreesOfType2FuzzySets.getLowMipsDegreeOfMembershipLowerBound() + "," +
					membershipDegreesOfType2FuzzySets.getLowMipsDegreeOfMembershipUpperBound() + "," +
					membershipDegreesOfType2FuzzySets.getMediumMipsDegreeOfMembershipLowerBound() + "," +
					membershipDegreesOfType2FuzzySets.getMediumMipsDegreeOfMembershipUpperBound() + "," +
					membershipDegreesOfType2FuzzySets.getHighMipsDegreeOfMembershipLowerBound() + "," +
					membershipDegreesOfType2FuzzySets.getHighMipsDegreeOfMembershipUpperBound() + "," +
					membershipDegreesOfType2FuzzySets.getLowStorageDegreeOfMembershipLowerBound() + "," +
					membershipDegreesOfType2FuzzySets.getLowStorageDegreeOfMembershipUpperBound() + "," +
					membershipDegreesOfType2FuzzySets.getMediumStorageDegreeOfMembershipLowerBound() + "," +
					membershipDegreesOfType2FuzzySets.getMediumStorageDegreeOfMembershipUpperBound() + "," +
					membershipDegreesOfType2FuzzySets.getHighStorageDegreeOfMembershipLowerBound() + "," +
					membershipDegreesOfType2FuzzySets.getHighStorageDegreeOfMembershipUpperBound() + "," +
					membershipDegreesOfType2FuzzySets.getLowEnergyDegreeOfMembershipLowerBound() + "," +
					membershipDegreesOfType2FuzzySets.getLowEnergyDegreeOfMembershipUpperBound() + "," +
					membershipDegreesOfType2FuzzySets.getMediumEnergyDegreeOfMembershipLowerBound() + "," +
					membershipDegreesOfType2FuzzySets.getMediumEnergyDegreeOfMembershipUpperBound() + "," +
					membershipDegreesOfType2FuzzySets.getHighEnergyDegreeOfMembershipLowerBound() + "," +
					membershipDegreesOfType2FuzzySets.getHighEnergyDegreeOfMembershipUpperBound() + "," +
					membershipDegreesOfType2FuzzySets.getUnderUtilizationDegreeOfMembershipLowerBound()+","+
					membershipDegreesOfType2FuzzySets.getUnderUtilizationDegreeOfMembershipUpperBound()+","+
					membershipDegreesOfType2FuzzySets.getNormalUtilizationDegreeOfMembershipLowerBound()+","+
					membershipDegreesOfType2FuzzySets.getNormalUtilizationDegreeOfMembershipUpperBound()+","+
					membershipDegreesOfType2FuzzySets.getOverUtilizationDegreeOfMembershipLowerBound()+","+
					membershipDegreesOfType2FuzzySets.getOverUtilizationDegreeOfMembershipUpperBound();
			
			libFile.adicionarLinhaTxt(linha, this.pathFile, this.fileName);
			
			/*System.out.println(membershipDegreesOfType2FuzzySets.getLowUtilizationDegreeOfMembershipLowerBound()+" - "+
					membershipDegreesOfType2FuzzySets.getHighUtilizationDegreeOfMembershipUpperBound());*/
		//}
		
		
	}
	

	public DaoMembershipDegreesOfType2FuzzySets() {
		super();
		
		
		
		//libFile.adicionarLinhaTxt(this.fileHeader, this.pathFile, this.fileName);
	}
	
	public void adicionarCabecalho() throws IOException {

		String fullFile = this.pathFile+this.fileName;
		 File arquivoLeitura = new File(fullFile);
		
		if (!arquivoLeitura.exists()) {
		
		//https://www.guj.com.br/t/quantidade-de-linhas-de-um-arquivo/45691/5
       
        //LineNumberReader linhaLeitura = new LineNumberReader(new FileReader(arquivoLeitura));
        //linhaLeitura.skip(arquivoLeitura.length());
        
        //if(linhaLeitura.getLineNumber() == 0)
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
