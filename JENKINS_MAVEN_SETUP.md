# Jenkins – Build stage (Maven via Docker)

Pipeline **Build** stage Maven ko **Docker container** ke andar chalaati hai. Isliye:

- **Maven (M3) Jenkins par configure karne ki zaroorat nahi.**
- **Docker** Jenkins server/agent par install hona chahiye, aur Jenkins user ko `docker run` chalane ki permission honi chahiye.

---

## Zaroorat: Docker

Jenkins jis machine par chal raha hai (agent) wahan:

1. Docker installed ho: `docker --version`
2. Jenkins process (jo jobs chalaati hai) ko Docker socket access ho – masalan agent same machine par ho jahan Docker daemon chal raha hai, aur jenkins user `docker` group mein ho:

   ```bash
   sudo usermod -aG docker jenkins
   # then restart Jenkins
   ```

Agar **Build** stage mein `docker: not found` ya "permission denied" aaye, to pehle Docker install/configure karo, phir pipeline dubara chalao.
