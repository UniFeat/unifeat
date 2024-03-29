 <div align="center" width="100px"> 
     <a href="https://unifeat.github.io/"><img src="https://raw.githubusercontent.com/UniFeat/UniFeat.github.io/main/images/logo.png" alt="logo" width="25%" /></a>
     <p font-size="300px"><b>Universal Feature Selection Tool</b></p> 
     <p font-size="100px"><b>Tool Repository</b></p> 
 </div>
 
 ----
[![GitHub stars](https://img.shields.io/github/stars/UniFeat/unifeat)](https://github.com/UniFeat/unifeat/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/UniFeat/unifeat)](https://github.com/UniFeat/unifeat/network)
[![DOI:10.1016/j.neucom.2023.03.037](https://zenodo.org/badge/DOI/10.1016/j.neucom.2023.03.037.svg)](https://doi.org/10.1016/j.neucom.2023.03.037)
[![DOI](https://zenodo.org/badge/DOI/10.5281/zenodo.8046034.svg)](https://doi.org/10.5281/zenodo.8046034)
[![GitHub license](https://img.shields.io/github/license/UniFeat/unifeat)](https://github.com/UniFeat/unifeat/blob/main/LICENSE)

[**User Manual**](https://unifeat.github.io/docs/user_manual_v1.1.pdf) |
[**JavaDoc**](https://unifeat.github.io/docs/java-docs/v0.1.1/index.html) 

The **Uni**versal **Feat**ure Selection Tool (UniFeat) is an open-source tool developed entirely in Java for performing feature selection process in different areas of research. The project aims to create a unified framework for researchers applying feature selection.

UniFeat provides a set of well-known and state-of-the-art feature selection methods within the essential auxiliary tools, including performance evaluation criteria, visual displays, statistical analysis, and reduced datasets to compare the performance of feature selection methods.

## Citation
Sina Tabakhi and Parham Moradi, [**Universal Feature Selection Tool (UniFeat): An Open-Source Tool for Dimensionality Reduction**](https://doi.org/10.1016/j.neucom.2023.03.037), Neurocomputing, Vol. 535, pp. 156-165, 2023.

If you find this repository useful, please cite our paper:
```
@article{tabakhi2023unifeat,
 title     = {Universal feature selection tool (UniFeat): An open-source tool for dimensionality reduction},
 author    = {Tabakhi, Sina and Moradi, Parham},
 journal   = {Neurocomputing},
 year      = {2023},
 volume    = {535},
 pages     = {156-165},
 publisher = {Elsevier},
 doi       = {10.1016/j.neucom.2023.03.037}
}
```



## Objectives
Our aim in the development of UniFeat as a comprehensive feature selection tool includes six aspects. 
1. UniFeat implements well-known and state-of-the-art feature selection methods within a unified framework. 
2. UniFeat can be considered a benchmark tool due to the development of methods in all feature selection approaches. 
3. The functions presented in UniFeat provide essential auxiliary tools needed for performance evaluation, results visualization, and statistical analysis. 
4. UniFeat has been implemented completely in Java, and can therefore be run on a wide range of platforms. 
5. Researchers are able to use UniFeat through its GUI environment or as a library in their Java codes. 
6. Finally, the open-source nature of UniFeat can help researchers use and modify the tool to fit their research requirements and greatly facilitate it for them to share their methods with the scientific community rapidly.

## Algorithms supported
In UniFeat, you can simply access to the well-known and state-of-the-art feature selection methods in the literature. The project has three tabs corresponding to the different feature selection approaches, including `Filter,` `Wrapper,` and `Embedded` tabs. In the `Filter,` `Wrapper,` and `Embedded` tabs, the UniFeat repository has involved the feature selection methods, the details of which are listed in Tables 1-3, respectively.

**Table 1**: Filter-based feature selection methods in the UniFeat repository.
|method	|supervised/unsupervised|multivariate/univariate|
|------	|-----------------------|-----------------------|
|Information gain|supervised|univariate|
|Gain ratio|supervised|univariate|
|Symmetrical uncertainty|supervised|univariate|
|Fisher score|supervised|univariate|
|Gini index|supervised|univariate|
|mRMR|supervised|multivariate|
|Laplacian score|supervised & unsupervised|univariate|
|Relevance-redundancy feature selection (RRFS)|supervised & unsupervised|multivariate|
|Term variance|unsupervised|univariate|
|Mutual correlation|unsupervised|multivariate|
|Random subspace method (RSM)|unsupervised|multivariate|
|UFSACO|unsupervised|multivariate|
|RRFSACO_1|unsupervised|multivariate|
|RRFSACO_2|unsupervised|multivariate|
|IRRFSACO_1|unsupervised|multivariate|
|IRRFSACO_2|unsupervised|multivariate|
|MGSACO|unsupervised|multivariate|

**Table 2**: Wrapper-based feature selection methods in the UniFeat repository.
|method|supervised/unsupervised|
|------|-----------------------|
|Binary particle swarm optimization (BPSO)|supervised|
|Continuous particle swarm optimization (CPSO)|supervised|
|Particle swarm optimization version 4-2 (PSO(4-2))|supervised|
|HPSO-LS|supervised|
|Simple GA|supervised|
|HGAFS|supervised|
|Optimal ACO|supervised|

**Table 3**: Embedded-based feature selection methods in the UniFeat repository.
|method|supervised/unsupervised|
|------|-----------------------|
|Decision tree based method|supervised|
|Random forest|supervised|
|SVM_RFE|supervised|
|MSVM_RFE|supervised|
|OVO_SVM_RFE|supervised|
|OVA_SVM_RFE|supervised|

More information is available at the [UniFeat Homepage](https://unifeat.github.io/).

## License
UniFeat is implemented and developed principally at the [University of Kurdistan](https://international.uok.ac.ir/en/), Sanandaj, Iran, and distributed under the [MIT License](https://github.com/UniFeat/unifeat/blob/main/LICENSE) terms.
