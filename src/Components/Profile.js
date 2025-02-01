import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { Box, Divider } from "@mui/material";
import LogoutIcon from "@mui/icons-material/Logout";
import EditIcon from "@mui/icons-material/Edit";
import "../styles/Profile.css";

export default function Profile() {
  return (
    <Box
      className="test position_Card bg-white rounded"
      style={{ paddingBottom: "0.75rem" }}
    >
      <Box className="test rounded" p="1rem 0">
        <div className="d-flex justify-content-between mb-4 ">
          <div></div>
          <div className="row">
            <div className="col-8">
              <h5 className="mb-0">بدرالدين وفروخي</h5>
              <p className="mb-0">0610719939</p>
            </div>
            <div className="col-4">
              <img
                src="https://upload.wikimedia.org/wikipedia/commons/4/48/Outdoors-man-portrait_%28cropped%29.jpg"
                alt="Profile"
                width="50"
                height="50"
                className="rounded-circle me-3"
              />
            </div>
          </div>
        </div>
      </Box>
      <Divider />
      <Box p="1rem 0">
        <div className="d-flex flex-row-reverse bd-highlight">
          <div></div>
          <h5>
            تعديل &nbsp;
            <EditIcon />
          </h5>
        </div>
      </Box>
      <Box md="1rem">
        <div className="d-flex flex-row-reverse bd-highlight">
          <div></div>
          <h5>
            خروج&nbsp;&nbsp;
            <LogoutIcon />
          </h5>
        </div>
      </Box>

      <Divider />
      <Box p="1rem 0">
        <div className="d-flex justify-content-center pt-3">
          <button className="btn btn-info text-white w-50">طلباتي</button>
        </div>
      </Box>
    </Box>
  );
}
