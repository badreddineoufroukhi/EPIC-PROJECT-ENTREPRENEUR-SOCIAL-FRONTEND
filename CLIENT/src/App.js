import "./App.css";
import "bootstrap/dist/css/bootstrap.min.css";
import "./styles/tables_workers_style.css";

import Header from "./Components/Header";
import TablesWorkers from "./Components/TablesWorkers";
import OuvrierList from "./Pages/Admin/OUVRIER/ListerLesOuvriers";
import ClientList from "./Pages/Admin/CLIENT/ListerLesClients";
import SousServiceList from "./Pages/Admin/SOUSSERVICE/ListerLesSousServices";
import DemandeList from "./Pages/Admin/DEMANDE/ListerLesDemandes";
import { BrowserRouter } from "react-router-dom";
import { Routes, Route } from "react-router-dom";
import ListeSousServices from "./Components/ListeSousServices";
import InfoSensibleWorker from "./Components/InfoSensibleWorker";
import UserDemande1 from "./Components/userDemande1";
import Profile from "./Components/Profile";

function App() {
  return (
    <div className="App">
      <main>
        <div className="row">
          {/* Première colonne (gauche) */}
          {/* <div className="col-12 col-md-1"> */}
          {/* Ce bloc disparaît en dessous de la taille "md" (medium) */}
          {/* </div> */}
          <div className="row">
            <Header />
          </div>
          
          {/* Colonne du profil */}
          <div className="col-12 col-md-2 d-none d-md-block">
            <Profile />
          </div>

          {/* Colonne centrale */}
          <div className="col-12 col-md-10">
            <BrowserRouter>
              <Routes>
                <Route path="/TablesWorkers" element={<TablesWorkers />} />
                <Route path="/OuvrierList" element={<OuvrierList />} />
                <Route path="/ClientList" element={<ClientList />} />
                <Route path="/SousServiceList" element={<SousServiceList />} />
                <Route path="/DemandeList" element={<DemandeList />} />

                <Route
                  path="/ListeSousServices/:id"
                  element={<ListeSousServices />}
                />
                <Route
                  path="/InfoSensibleWorker/:id"
                  element={<InfoSensibleWorker />}
                />
                <Route path="/UserDemande" element={<UserDemande1 />} />
              </Routes>
            </BrowserRouter>
          </div>
        </div>
      </main>
    </div>
  );
}

export default App;
