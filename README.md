# CloudSim: A Framework For Modeling And Simulation Of Cloud Computing Infrastructures And Services


## Introduction
Recently, cloud computing emerged as the leading technology for delivering reliable, secure, fault-tolerant, sustainable, and scalable computational services, which are presented as Software, Infrastructure, or Platform as services (SaaS, IaaS, PaaS). Moreover, these services may be offered in private data centers (private clouds), may be commercially offered for clients (public clouds), or yet it is possible that both public and private clouds are combined in hybrid clouds.

These already wide ecosystem of cloud architectures, along with the increasing demand for energy-efficient IT technologies, demand timely, repeatable, and controllable methodologies for evaluation of algorithms, applications, and policies before actual development of cloud products. Because utilization of real testbeds limits the experiments to the scale of the testbed and makes the reproduction of results an extremely difficult undertaking, alternative approaches for testing and experimentation leverage development of new Cloud technologies.

A suitable alternative is the utilization of simulations tools, which open the possibility of evaluating the hypothesis prior to software development in an environment where one can reproduce tests. Specifically in the case of Cloud computing, where access to the infrastructure incurs payments in real currency, simulation-based approaches offer significant benefits, as it allows Cloud customers to test their services in repeatable and controllable environment free of cost, and to tune the performance bottlenecks before deploying on real Clouds. At the provider side, simulation environments allow evaluation of different kinds of resource leasing scenarios under varying load and pricing distributions. Such studies could aid the providers in optimizing the resource access cost with focus on improving profits. In the absence of such simulation platforms, Cloud customers and providers have to rely either on theoretical and imprecise evaluations, or on try-and-error approaches that lead to inefficient service performance and revenue generation.

The primary objective of this project is to provide a generalized, and extensible simulation framework that enables seamless modeling, simulation, and experimentation of emerging Cloud computing infrastructures and application services. By using CloudSim, researchers and industry-based developers can focus on specific system design issues that they want to investigate, without getting concerned about the low level details related to Cloud-based infrastructures and services.


## Main features
Overview of CloudSim functionalities:

- support for modeling and simulation of large scale Cloud computing data centers
- support for modeling and simulation of virtualized server hosts, with customizable policies for provisioning host resources to virtual machines
- support for modeling and simulation of application containers
- support for modeling and simulation of energy-aware computational resources
- support for modeling and simulation of data center network topologies and message-passing applications
- support for modeling and simulation of federated clouds
- support for dynamic insertion of simulation elements, stop and resume of simulation
- support for user-defined policies for allocation of hosts to virtual machines and policies for allocation of host resources to virtual machines

### More information on the project page at:
http://www.cloudbus.org/cloudsim/

## Here

A modified version of CloudSim contemplating an approach with a type-2 fuzzy system (T2FS) for the treatment of variable uncertainties and inaccuracies, communication cost (CC), computational power (CP), and level of RAM usage.

The T2FS module called Interval Fuzzy Load Balancing for Cloud Computing (Int-FLBCC) that integrates with CloudSim, is modeled and implemented with Juzzy [[1]](http://juzzy.wagnerweb.net/) and is available in the [GitHub](https://github.com/brunomourapaz/Juzzy) repository.

## Publications
1. B. M. P. Moura, G. B. Schneider, A. C. Yamin, M. L. Pilla and R. H. S. Reiser, ["Allocating Virtual Machines exploring Type-2 Fuzzy Logic and Admissible Orders,"](https://ieeexplore.ieee.org/document/8858827) 2019 IEEE International Conference on Fuzzy Systems (FUZZ-IEEE), New Orleans, LA, USA, 2019, pp. 1-6, doi: 10.1109/FUZZ-IEEE.2019.8858827.
2. Moura, B., Schneider, G., Yamin, A., Pilla, M., & Reiser, R. (2019, August). [Type-2 Fuzzy Logic Approach for Overloaded Hosts in Consolidation of Virtual Machines in Cloud Computing.](https://www.atlantis-press.com/proceedings/eusflat-19/125914863) In 11th Conference of the European Society for Fuzzy Logic and Technology (EUSFLAT 2019) (pp. 668-675). Atlantis Press. 
3. Moura, B. M., Schneider, G. B., Yamin, A. C., Santos, H., Reiser, R. H., & Bedregal, B. (2021). [Interval-valued Fuzzy Logic approach for overloaded hosts in consolidation of virtual machines in cloud computing.](https://www.sciencedirect.com/science/article/abs/pii/S0165011421000762) Fuzzy Sets and Systems. 

## Reference
[1] [C. Wagner, "Juzzy – A Java based Toolkit for Type-2 Fuzzy Logic", Proceedings of the IEEE Symposium Series on Computational Intelligence, Singapore, April 2013.](http://juzzy.wagnerweb.net/)

