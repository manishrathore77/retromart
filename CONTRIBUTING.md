# 🤝 Contributing to RetroMart

Thanks for wanting to contribute to RetroMart! 📼🕹️

## 🚀 Getting Started

1. Fork the repository
2. Clone your fork: `git clone https://github.com/YOUR_USERNAME/retromart.git`
3. Create a branch: `git checkout -b feature/amazing-feature`
4. Make your changes
5. Commit: `git commit -m "feat: add amazing feature"`
6. Push: `git push origin feature/amazing-feature`
7. Open a Pull Request

## 📝 Commit Message Convention

We use [Conventional Commits](https://www.conventionalcommits.org/):

- `feat:` — New feature
- `fix:` — Bug fix
- `docs:` — Documentation only
- `style:` — Code style changes
- `refactor:` — Code refactoring
- `test:` — Adding tests
- `chore:` — Build process or auxiliary tools

## 🛠️ Development Setup

### Prerequisites
- Java 17+
- Maven 3.8+
- MySQL 8.0+

### Database Setup
```sql
CREATE DATABASE retrodb;
CREATE USER 'retrouser'@'localhost' IDENTIFIED BY 'retrouser';
GRANT ALL ON retrodb.* TO 'retrouser'@'localhost';
```

## 📜 Code of Conduct

Be excellent to each other. This is a retro-themed project — keep the vibes groovy! ✌️
