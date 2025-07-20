# Hybrid-FLBCC: Virtual Machine Consolidation in Cloud Infrastructures Exploring Flexible Computing


## Introduction
Cloud Computing (CC) enables elastic and on-demand access to computational resources. 

As its adoption grows across sectors, the challenge of maintaining performance while minimizing energy consumption and operational costs becomes increasingly relevant.

A critical strategy to address these concerns is Virtual Machine (VM) consolidation, which involves dynamically reallocating VMs to reduce the number of active physical hosts. 

However, deciding when and where to migrate VMs is inherently uncertain and context-dependent.

Fuzzy Logic (FL), known for its ability to model imprecision, has been applied in CC resource management. In parallel, Machine Learning (ML) methods offer adaptive and data-driven models for classification and decision-making.

## Main features
Hybrid-FLBCC enhances the [Int-FLBCC](https://github.com/brunomourapaz/Juzzy) system, which is based on the [Juzzy](http://juzzy.wagnerweb.net/) framework for fuzzy logic and integrated into the [CloudSim](http://www.cloudbus.org/cloudsim/) simulator as a module for resource management experimentation. It incorporates feature selection algorithms and adaptive fuzzy logic components to improve decision-making in virtualized environments.

The approach explores combinations of:
- VM Allocation Policies (AP): Inter Quartile Range (IQR), Local Regression (LR), Local Regression Robust (LRR), and Median Absolute Deviation (MAD).
- VM Selection Policies (SP): Maximum Correlation (MC), Minimum Migration Time (MMT), Minimum Utilization (MU), and Random Selection (RS).

For each AP×SP combination, a separate fuzzy inference model is configured.

The core steps include:

- Feature Selection: Applying Sequential Forward Selection (SFS) to reduce dimensionality and improve performance;
- Fuzzy Classification: Using FRBCS algorithms (FARC-HD, Chi, FURIA, IVTURS) to construct interpretable rule bases;
- Policy-Specific Inference: Generating fuzzy rules and membership functions per configuration, tailored to dynamic resource utilization patterns.

This structure promotes scalability and adaptability in VM consolidation strategies.

## Hybrid-FLBCC

Hybrid-FLBCC introduces a flexible, data-driven framework for server consolidation, integrating fuzzy logic and ML for smarter and more interpretable decision-making.

The solution builds upon the integration of fuzzy rule-based classifiers (via Juzzy) within the CloudSim simulation environment, allowing for reproducible, scalable experimentation with real-world-inspired workloads.

### Contributions
- Adaptive fuzzy systems tuned to specific allocation–selection policy pairs;
- Enhanced classification accuracy and generalization;
- Efficient use of computational resources via reduced input dimensions.

## Publications
1. Rafael R. Bastos, Vagner A. Seibert, Gabriel R. Silva, Bruno M. P. Moura, Giancarlo Lucca, Adenauer C. Yamin, Renata H. R. Reiser. ["Uma Revisão Sistemática sobre Consolidação de Servidores em Ambientes de Computação em Nuvem via Lógica Fuzzy"](https://sol.sbc.org.br/index.php/weit/article/view/26605). In Workshop-Escola de Informática Teórica (WEIT), 2023. DOI: 10.5753/weit.2023.26605
2. Rafael Bastos, Vagner Seibert, Giovani Maia, Bruno P. de Moura, Giancarlo Lucca, Adenauer Yamin, Renata Reiser. ["Exploratory Data Analysis in Cloud Computing Environments for Server Consolidation via Fuzzy Classification Models"](https://www.scitepress.org/Link.aspx?doi=10.5220/0012615900003690). In 26th International Conference on Enterprise Information Systems (ICEIS), 2024. DOI: 10.5220/0012615900003690
3. Rafael R Bastos, Bruno Moura Paz de Moura, Giancarlo Lucca, Helida Salles Santos, Adenauer Correa Yamin, Renata Reiser. "Hybrid-FLBCC: Virtual Machine Consolidation in Cloud Infrastructures Exploring Flexible Computing". In IEEE International Conference on Fuzzy Systems (FUZZ), 2025.
4. R.R. Bastos, B.M.P. Moura, H.S. Santos, G. Lucca, A.C. Yamin, R.H.S. Reiser. ["Enhancing a fuzzy system through computational intelligence-based feature selection for decision-making in cloud computing environments"](https://www.sciencedirect.com/science/article/abs/pii/S0167739X25002833). Future Generation Computer Systems, Volume 174, 2026. DOI: 10.1016/j.future.2025.107988
## References
[1] C. Wagner. *Juzzy – A Java-based Toolkit for Type-2 Fuzzy Logic*. Proceedings of the IEEE Symposium Series on Computational Intelligence, Singapore, 2013. [http://juzzy.wagnerweb.net/](http://juzzy.wagnerweb.net/)
[2] R. N. Calheiros, R. Ranjan, A. Beloglazov, C. A. De Rose, R. Buyya. *CloudSim: A Toolkit for Modeling and Simulation of Cloud Computing Environments and Evaluation of Resource Provisioning Algorithms*. *Software: Practice and Experience*, 41(1), 2011, pp. 23–50. [http://www.cloudbus.org/cloudsim/](http://www.cloudbus.org/cloudsim/)
[3] B. M. P. Moura, G. B. Schneider, A. C. Yamin, M. L. Pilla, R. H. S. Reiser. *Allocating Virtual Machines Exploring Type-2 Fuzzy Logic and Admissible Orders*. In: *Proceedings of the 2019 IEEE International Conference on Fuzzy Systems (FUZZ-IEEE)*, New Orleans, LA, USA, 2019, pp. 1–6. DOI: [10.1109/FUZZ-IEEE.2019.8858827](https://doi.org/10.1109/FUZZ-IEEE.2019.8858827)
