Command line instructions


Git global setup

git config --global user.name "sunke"
git config --global user.email "sunke@aspirecn.com"

Create a new repository

git clone http://10.12.3.141/MES/MES_PROJECT.git
cd MES_PROJECT
touch README.md
git add README.md
git commit -m "add README"
git push -u origin master

Existing folder

cd existing_folder
git init
git remote add origin http://10.12.3.141/MES/MES_PROJECT.git
git add .
git commit
git push -u origin master

Existing Git repository

cd existing_repo
git remote add origin http://10.12.3.141/MES/MES_PROJECT.git
git push -u origin --all
git push -u origin --tags