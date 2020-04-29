# Feature Modelling

* A set of programs is considered a **family** if:

  * It makes sense to study their common properties first
  * Then, special properties of individual family members are studied

* Software product line engineering

  * Factoring out commonalities for reuse
  * Managing variabilities for software customization

* Benefits of software product lines

  * Improve product realiability
  * Improve usability
  * Improve consistency across products
  * Reduce production costs
  * Shorten time-to-market

* It can be more challenging to develop a SPL than just a single piece of software

* Variability introduces complexity

* SPLs have higher short-term cost but lower long-term cost

* Feature models

  * A configuration = a specific selection of features

  * A configutation is *valid* if all mandatary features are selected but *incomplete* if one or more decompositions are not satisfied
  * *Complete* if all decompositions are satisfied

* If a child feature is selected, its parent must also be selected

