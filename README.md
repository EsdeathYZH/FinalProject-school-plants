<h1><em><bold>Project：School-Plants in SJTU</bold></em></h1>
Programming language：Java、Android<br>
IDE：Android Studio

<h2>Git Workflow</h2>
<h3>I.Create repository in Github</h3>
Find the repository you want to contribute and fork it.
Or you can click the "new" button to create your own repository.
<h3>II.Write your own code and commit</h3>
1.If your repository is created by you,you can enter the dictionary by "cd" command.Then input "git init" to create local repository.<br><br>
2.If your repository is forked from another repository.Your need to use"git clone" to clone remote repository to the current dictionary.<br><br>
3.Coding and coding.....<br><br>
4.Use "git remote add origin https://github.com/EsdeathYZH/FinalProject-school-plants.git" to create remote repository.<br><br>
5.Input "git add ."to add the infomation of files to the
index database.Your can also use "git reset HEAD file" to give up your changes from the index database.Then you can use "git checkout -- file" to give uo your changes from your project dictionary.<br><br>
6.Use "git commit" to commit your change to your repository.Use "git commit -m"some comments" " to make some comments on this commit.<br><br>
7.Use "git push &lt;remote repository&gt; &lt;local branch&gt;:&lt;remote branch&gt;" to push your repository to remote repository.For example:"git push origin master"<br><br>
<h3>III.Pull request</h3>
<em>This step occurs when your remote repository is forked from another.</em><br>

1.If your want to commit your change to the origin repository.Your need to pull request.<br><br>
2.Now the owner of origin repository can check your commit.If there is no conflicts,it means your changes can automatically merged into origin repository.<br><br>
3.Every time you begin coding, you can use "git pull &lt;remote repository> &lt;remote branch&gt;:&lt;local branch&gt;" to update your project."git pull" equals to "git fetch"+"git merge".<br><br>


![](git.jpg)
