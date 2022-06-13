ProjetTestAngular
Ce projet  est crée avec Angular CLI version 11.0.3.
Serveur de développement
Pour exécuter ce projet j’utilise la commande ng serve.
Accédez à la page web il faut tapez cette http://localhost:4200/ dans la barre de recherche de votre 
navigateur. L'application se rechargera automatiquement.
Effet j'ai utilisé un authentification base comme LocalStorage. 
Les cordonnes de connexion de l'utiliser son enregistre dans le navigateur.
Il y'a deux type de profile : admin et user 
Voici les cordonnes des utilisateurs(username : admin et password: 1234,  username user1 et password: 1234, username : user2 et  )
J'ai fait un contrôle qui permettre de dire seul les utilisateurs ayant le profile admin peut voir le 
buttons Update (Donc pour voire le buttons update il faut se connecte en tant que admin et non tant qu’user).
Les technologies installer :
Bootstrap
Jquery et font-awesome

Échafaudage de code
Exécutez ng generate component nom-composant pour générer un nouveau composant. Vous pouvez également utiliser ng generate directive|pipe|service|class|guard|interface|enum|module.
Exécution de tests unitaires
Exécutez ng test pour exécuter les tests unitaires via Karma.

Exécution de tests de bout en bout
Exécutez ng e2e pour exécuter les tests de bout en bout via Protractor.
