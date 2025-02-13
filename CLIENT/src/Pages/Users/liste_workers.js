import React from "react";
import TablesWorkers from "../../Components/TablesWorkers";
import Profile from "../../Components/Profile";

export default function liste_workers() {
  return (
    <div className="row">
      <div className="col-10">
        <TablesWorkers />
      </div>
      <div className="col-2">
        <Profile />
      </div>
    </div>
  );
}
