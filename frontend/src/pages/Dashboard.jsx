
import { useNavigate } from "react-router-dom";

export default function Dashboard() {
  const navigate = useNavigate();

  let user = {};
  try {
    user = JSON.parse(sessionStorage.getItem("user") || "{}");
  } catch {
    user = {};
  }

  function logout() {
    sessionStorage.removeItem("token");
    sessionStorage.removeItem("user");
    navigate("/login", { replace: true });
  }

  return (
    <main className="dashboard">
      <header className="topbar">
        <div className="brand"><span>N</span> N-Bank</div>
        <button className="logout-button" onClick={logout}>
          Logout
        </button>
      </header>

      <section className="welcome">
        <p className="eyebrow">PERSONAL BANKING</p>
        <h1>Welcome, {user.name || "Customer"}!</h1>
        <p className="muted">
          Управляйте своим банковским аккаунтом в одном месте.
        </p>
      </section>

      <section className="dashboard-grid">
        <article className="dashboard-card balance-card">
          <p>Account balance</p>
          <h2>Not connected yet</h2>
          <span>Баланс появится после подключения защищённого API счёта.</span>
        </article>

        <article className="dashboard-card">
          <p className="card-icon">◉</p>
          <h3>Profile</h3>
          <p className="muted">Email: {user.email || "Не указан"}</p>
          <span>Информация о текущем пользователе.</span>
        </article>

        <article className="dashboard-card">
          <p className="card-icon">↗</p>
          <h3>Transactions</h3>
          <p className="muted">История операций</p>
          <span>Функция будет подключена к существующему API.</span>
        </article>

        <article className="dashboard-card">
          <p className="card-icon">＋</p>
          <h3>Bank accounts</h3>
          <p className="muted">Управление счетами</p>
          <span>Функция будет подключена к существующему API.</span>
        </article>
      </section>

      <p className="dashboard-note">
        Демонстрационная панель. Баланс и операции не подменяются тестовыми данными.
      </p>
    </main>
  );
}
