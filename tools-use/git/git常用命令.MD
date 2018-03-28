clone操作
git clone git@10.148.68.13:Ocean/Europe.git

下载远程分支到本地
 git checkout -b featrue/doc  origin

更新 
Git pull origin feature/code

 切换分支
Git checkout feature/code

 提交三连击
Git add .
Git commit -m "commit code"
git push origin feature/code

创建分支
Git branch feature/code

提交分支到远程
Git push origin feature/code

删除分支
【git 删除本地分支】
git branch -D feature/code
 
【git 删除远程分支】
git push origin :feature/code  (origin 后面有空格)

取消合并
 git reset --hard

问题解决方式
To 10.148.68.13:Ocean/Europe.git
 ! [rejected]        feature/doc -> feature/doc (non-fast-forward)
error: failed to push some refs to 'git@10.148.68.13:Ocean/Europe.git'

git pull --rebase origin master
git push -u origin master

  

比较
git diff --color > foo.diff
foo.diff用notepad++/sublime 之类的编辑器打开，高亮颜色
git diff [branchA] [branchB] >>d:/diff/exportname.diff

代码合并
Step 1. Update the repo and checkout the branch we are going to merge

git fetch origin
git checkout -b feature/review830 origin/feature/review830
Step 2. Merge the branch and push the changes to GitLab

git checkout feature/20170915
git merge --no-ff feature/review830
git push origin feature/20170915
