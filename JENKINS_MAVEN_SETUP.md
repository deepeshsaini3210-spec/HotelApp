# Jenkins par Maven setup (mvn: not found fix)

Pipeline **Build** stage ke liye Jenkins ko Maven chahiye. Neeche dono mein se koi **ek** karo.

---

## Option 1: Jenkins Global Tool Configuration (recommended)

1. Jenkins dashboard → **Manage Jenkins** → **Global Tool Configuration**.
2. **Maven** section tak scroll karo.
3. **Add Maven** click karo.
4. **Name** mein exactly ye likho: `M3`  
   (Jenkinsfile mein `tools { maven 'M3' }` use ho raha hai, isliye name `M3` hona chahiye.)
5. **Install automatically** select karo.
6. **Save** karo.

Iske baad pipeline dubara run karo; **Build** stage mein `mvn` mil jayega.

---

## Option 2: Maven server par install karo

Agar Global Tool Configuration use nahi karna:

**Ubuntu/Debian (Jenkins server par):**
```bash
sudo apt-get update
sudo apt-get install -y maven
```

**RHEL/CentOS:**
```bash
sudo yum install -y maven
```

Phir Jenkinsfile se `tools { maven 'M3' }` block **hata do** (taaki pipeline system PATH wala `mvn` use kare):

```groovy
pipeline {
    agent any
    // tools { maven 'M3' }   <- comment out or delete
    ...
}
```

Save karke pipeline phir run karo.
