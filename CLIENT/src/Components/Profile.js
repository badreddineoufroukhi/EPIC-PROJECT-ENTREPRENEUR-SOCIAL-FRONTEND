// import React from "react";
// import "bootstrap/dist/css/bootstrap.min.css";
// import { Box, Divider } from "@mui/material";
// import LogoutIcon from "@mui/icons-material/Logout";
// import EditIcon from "@mui/icons-material/Edit";
// import "../styles/Profile.css";

// export default function Profile() {
//   return (
//     <Box
//       className="test position_Card bg-white rounded"
//       style={{ paddingBottom: "0.75rem" }}
//     >
//       <Box className="test rounded" p="1rem 0">
//         <div className="d-flex justify-content-between mb-4 ">
//           <div></div>
//           <div className="row">
//             <div className="col-8">
//               <h5 className="mb-0">بدرالدين وفروخي</h5>
//               <p className="mb-0">0610719939</p>
//             </div>
//             <div className="col-4">
//               <img
//                 src="https://upload.wikimedia.org/wikipedia/commons/4/48/Outdoors-man-portrait_%28cropped%29.jpg"
//                 alt="Profile"
//                 width="50"
//                 height="50"
//                 className="rounded-circle me-3"
//               />
//             </div>
//           </div>
//         </div>
//       </Box>
//       <Divider />
//       <Box p="1rem 0">
//         <div className="d-flex flex-row-reverse bd-highlight">
//           <div></div>
//           <h5>
//             تعديل &nbsp;
//             <EditIcon />
//           </h5>
//         </div>
//       </Box>
//       <Box md="1rem">
//         <div className="d-flex flex-row-reverse bd-highlight">
//           <div></div>
//           <h5>
//             خروج&nbsp;&nbsp;
//             <LogoutIcon />
//           </h5>
//         </div>
//       </Box>

//       <Divider />
//       <Box p="1rem 0">
//         <div className="d-flex justify-content-center pt-3">
//           <button className="btn btn-info text-white w-50">طلباتي</button>
//         </div>
//       </Box>
//     </Box>
//   );
// }



"use client"

import * as React from "react"
import {
  Box,
  Drawer,
  List,
  ListItem,
  ListItemButton,
  ListItemIcon,
  ListItemText,
  Collapse,
  Divider,
} from "@mui/material"
import { Home, Person, Settings, Mail, Dashboard, ExpandLess, ExpandMore, StarBorder, Inbox } from "@mui/icons-material"

const drawerWidth = 240

export default function VerticalMenu() {
  const [open, setOpen] = React.useState(true)

  const handleClick = () => {
    setOpen(!open)
  }

  return (
    <Box sx={{ display: "flex" }}>
      <Drawer
        variant="permanent"
        sx={{
          width: drawerWidth,
          flexShrink: 0,
          "& .MuiDrawer-paper": {
            width: drawerWidth,
            boxSizing: "border-box",
          },
        }}
      >
        <Box sx={{ overflow: "auto", mt: 2 }}>
          <List>
            <ListItem disablePadding>
              <ListItemButton>
                <ListItemIcon>
                  <Home />
                </ListItemIcon>
                <ListItemText primary="Accueil" />
              </ListItemButton>
            </ListItem>

            <ListItem disablePadding>
              <ListItemButton>
                <ListItemIcon>
                  <Dashboard />
                </ListItemIcon>
                <ListItemText primary="Dashboard" />
              </ListItemButton>
            </ListItem>

            <ListItemButton onClick={handleClick}>
              <ListItemIcon>
                <Inbox />
              </ListItemIcon>
              <ListItemText primary="Messages" />
              {open ? <ExpandLess /> : <ExpandMore />}
            </ListItemButton>
            <Collapse in={open} timeout="auto" unmountOnExit>
              <List component="div" disablePadding>
                <ListItemButton sx={{ pl: 4 }}>
                  <ListItemIcon>
                    <StarBorder />
                  </ListItemIcon>
                  <ListItemText primary="Messages importants" />
                </ListItemButton>
                <ListItemButton sx={{ pl: 4 }}>
                  <ListItemIcon>
                    <Mail />
                  </ListItemIcon>
                  <ListItemText primary="Boîte de réception" />
                </ListItemButton>
              </List>
            </Collapse>

            <Divider sx={{ my: 2 }} />

            <ListItem disablePadding>
              <ListItemButton>
                <ListItemIcon>
                  <Person />
                </ListItemIcon>
                <ListItemText primary="Profil" />
              </ListItemButton>
            </ListItem>

            <ListItem disablePadding>
              <ListItemButton>
                <ListItemIcon>
                  <Settings />
                </ListItemIcon>
                <ListItemText primary="Paramètres" />
              </ListItemButton>
            </ListItem>
          </List>
        </Box>
      </Drawer>
      {/* <Box component="main" sx={{ flexGrow: 1, p: 3 }}>
        <h1 className="text-2xl font-bold mb-4">Contenu Principal</h1>
        <p>Sélectionnez une option dans le menu.</p>
      </Box> */}
    </Box>
  )
}

