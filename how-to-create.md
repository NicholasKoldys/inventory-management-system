```
mvn "archetype:generate" "-D groupId=dev.nicholaskoldys.inventory_managment_system" "-D artifactId=inventory-management-system" "-D archetypeArtifactId=maven-archetype-quickstart"
```
```
git init
```
```
git remote add oririn <repolink>
```
```
git remote -v 
```
```
git push origin <branchToPush>
```
Or, if their are any *different/new* files in the destination repo, then..
```
git pull origin <branchToPull> --allow-unrelated-histories

git push origin <branchToPush>
```