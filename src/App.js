import "./App.css";
import "bootstrap/dist/css/bootstrap.min.css";
import "./styles/tables_workers_style.css";

import Header from "./Components/Header";
import TablesWorkers from "./Components/TablesWorkers";
import { BrowserRouter } from "react-router-dom";
import { Routes, Route } from "react-router-dom";
import ListeSousServices from "./Components/ListeSousServices";
import InfoSensibleWorker from "./Components/InfoSensibleWorker";
import UserDemande from "./Components/UserDemande";
import Profile from "./Components/Profile";

function App() {
  return (
    <div className="App">
      <Header />
      <main>
       
        <div className="row">
          {/* Première colonne (gauche) */}
          <div className="col-12 col-md-3">
            {/* Ce bloc disparaît en dessous de la taille "md" (medium) */}
          </div>

          {/* Colonne centrale */}
          <div className="col-12 col-md-6">
            <BrowserRouter>
              <Routes>
                <Route path="/TablesWorkers" element={<TablesWorkers />} />
                <Route
                  path="/ListeSousServices/:id"
                  element={<ListeSousServices />}
                />
                <Route
                  path="/InfoSensibleWorker/:id"
                  element={<InfoSensibleWorker />}
                />
                <Route path="/UserDemande" element={<UserDemande />} />
              </Routes>
            </BrowserRouter>
          </div>

          {/* Colonne du profil */}
          <div className="col-12 col-md-3 d-none d-md-block">
            <Profile />
          </div>
        </div>
      </main>
    </div>
  );
}

export default App;
