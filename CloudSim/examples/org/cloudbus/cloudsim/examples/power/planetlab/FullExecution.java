package org.cloudbus.cloudsim.examples.power.planetlab;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FullExecution {

	public static void main(String[] args) throws IOException {
		boolean enableOutput = false;
		boolean outputToFile = false;
		String inputFolder = FullExecution.class.getClassLoader().getResource("workload/planetlab").getPath();
		// String inputFolder = "/CloudSim/examples/workload/planetlab";
		String outputFolder = "output";

		// PlanetLab workload - 20110303, 20110306, 20110309, 20110322, 20110325,
		// 20110403, 20110409, 20110411, 20110412
		String[] workload = {"20110303","20110306","20110309","20110322", "20110325","20110403","20110409","20110411","20110412","20110420"};
		// workload que deu erro
		//String[] workload = { "20110303" };

		// Inter Quartile Range (IQR) VM allocation policy
		String[] vmAllocationPolicy = { "iqr", "lrr", "mad", "thr" }; // { "iqr", "lr", "lrr", "mad", "thr" };

		String vmSelectionPolicy = "rs"; // Random Selection (RS) VM selection policy
		String parameter = "0"; // 1.5 the safety parameter of the IQR policy
		boolean outputAbstractInCsv = true; // enable summary recording in csv
		boolean enableFuzzyT2Overload = true; // enable overload fuzzy type 2 detection

		// default value empty max(xInf, yInf), min(xSup, ySup)
		// String[] typeIntersection = { "o2", "om", "odb", "om3", "o2db", "a", "tl", "o2", "om", "odb", "om3", "o2db", "a", "tl", "maxmin"}; 
		
		// setanado as intersecção part2
		String[] typeIntersection = { "omix", "omix", "o2db"};

		// default value empty min(xInf, yInf), max(xSup, ySup)
		//String[] typeUnion = { "minmax", "minmax", "minmax", "minmax", "minmax", "minmax", "minmax",  "o2n", "omn", "odbn", "om3n", "o2dbn", "an", "tln", "minmax"};
		
		// setando as uniões part2
		String[] typeUnion = { "minmax", "omixn", "o2dbn"};
		
		int typeReductionType = 0; // CENTEROFSETS = 0; CENTROID = 1;
		int typeFuzzySystem = 0;  //  0 - Conventional Type-2 Fuzzy System, 1 - N Dimensional Type-2 Fuzzy Fuzzy System
		
		

		boolean admissibleOrders = false; // enable selection host for admissible orders
		String[] orderType = { "lex1", "lex2", "xuandyager" }; // set admissible order type

		// percorre e seta os workloads
		System.out.println("Iniciando a execução em: " + getDateTime());

		for (int i = 0; i < workload.length; i++) {

			// percorre e seta os algoritmos de alocação
			for (int x = 0; x < vmAllocationPolicy.length; x++) {

				// seta o valor da variável parameter de acordo com os valores default do
				// CloudSim
				if (vmAllocationPolicy[x].equals("iqr")) {
					parameter = "1.5";
				} else if (vmAllocationPolicy[x].equals("lr") || vmAllocationPolicy[x].equals("lrr")) {
					parameter = "1.2";
				} else if (vmAllocationPolicy[x].equals("mad")) {
					parameter = "2.5";

				} else if (vmAllocationPolicy[x].equals("thr")) {
					parameter = "0.8";

				}

				// verifica se a detecção de sobrecarga fuzzy ou ordens admissíveis esta
				// ativado, se não, executa sem fuzzy
				if ((enableFuzzyT2Overload) || (admissibleOrders)) {

					// percorre e seta as combinações de intersecção e união
					for (int y = 0; y < typeIntersection.length; y++) {

						// percorre e seta as uniões
						//for (int z = 0; z < typeUnion.length; z++) {

							// verifica se usar as ordens adimissíveis
							if (admissibleOrders) {

								for (int o = 0; o < orderType.length; o++) {

									
									//System.out.println("workload: "+workload[i]+" vmAllocationPolicy: "+vmAllocationPolicy[i]+" typeIntersection: "+typeIntersection[y]+" typeUnion: "+typeUnion[y]+ " order adimissible: "+orderType[o]);
									// construtor com ordens admissíveis e funções de agregação (intersecções e  uniões)
									new PlanetLabRunner(enableOutput, outputToFile, inputFolder, outputFolder,
											workload[i], vmAllocationPolicy[x], vmSelectionPolicy, parameter,
											outputAbstractInCsv, enableFuzzyT2Overload, typeIntersection[y],
											typeUnion[y], admissibleOrders, orderType[o], typeReductionType, typeFuzzySystem);
								}

							} else {

								// construtor sem ordens admissíveis com funções de agragação
								new PlanetLabRunner(enableOutput, outputToFile, inputFolder, outputFolder, workload[i],
										vmAllocationPolicy[x], vmSelectionPolicy, parameter, outputAbstractInCsv,
										enableFuzzyT2Overload, typeIntersection[y], typeUnion[y], typeReductionType, typeFuzzySystem);
							}

						//}

					}

					// se não utiliza detecção de sobre carga fuzzy e ordens admissíveis não precisa
					// percorrer as combinações de intersecções e uniões
				} else {

					// construtor padrão do CloudSim
					new PlanetLabRunner(enableOutput, outputToFile, inputFolder, outputFolder, workload[i],
							vmAllocationPolicy[x], vmSelectionPolicy, parameter, outputAbstractInCsv);

				}

			}

		}
		System.out.println("Fim da execução em: " + getDateTime());

	}

	private static String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

}
