"use client";
import React, { useState, useEffect } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
// import { Chip } from "@mui/material";

import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Button,
  TextField,
  Checkbox,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  IconButton,
  Typography,
  Alert,
  CircularProgress,
  Grid
} from "@mui/material";
import {
  Search,
  Refresh,
  Add,
  Visibility,
  Edit,
  Delete,
  KeyboardArrowUp,
  KeyboardArrowDown,
} from "@mui/icons-material";

export default function ClientList() {
  const [clients, setClients] = useState([]);
  const [loading, setLoading] = useState(false);
  const [total, setTotal] = useState(0);
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(5);
  const [selectedClients, setSelectedClients] = useState([]);
  const [deleteDialogOpen, setDeleteDialogOpen] = useState(false);
  const [sortField, setSortField] = useState("");
  const [sortOrder, setSortOrder] = useState("desc");
  const [filters, setFilters] = useState({});
  const [error, setError] = useState("");

  const fetchClients = async () => {
    setLoading(true);
    try {
      const payload = {
        page,
        maxResults: rowsPerPage,
        sortField,
        sortOrder,
        ...filters,
      };

      const response = await axios.post(
        "http://localhost:8080/client/paginatedListByCriteria",
        payload,
        {
          headers: { "Content-Type": "application/json" },
        }
      );

      const data = response.data;
      setClients(data.list || []);
      setTotal(data.dataSize || 0);
      setError("");
    } catch (error) {
      console.error("Error fetching clients:", error);
      setError("Erreur lors du chargement des OUVRIERS");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchClients();
  }, [page, rowsPerPage, sortField, sortOrder]);

  const handleSort = (column) => {
    const isAsc = sortField === column && sortOrder === "asc";
    setSortOrder(isAsc ? "desc" : "asc");
    setSortField(column);
  };

  const handleSelectAll = (event) => {
    setSelectedClients(event.target.checked ? clients : []);
  };

  const handleSelectClient = (client) => {
    setSelectedClients((prevSelected) => {
      const isSelected = prevSelected.find((h) => h.id === client.id);
      return isSelected
        ? prevSelected.filter((h) => h.id !== client.id)
        : [...prevSelected, client];
    });
  };

  const handleDelete = async () => {
    const selectedClientsToSend = selectedClients.map(
      (selectedClient) => selectedClient.id
    );
    try {
      await fetch("http://localhost:8080/client/delete", {
        method: "DELETE",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(selectedClientsToSend),
      });
      setDeleteDialogOpen(false);
      setSelectedClients([]);
      fetchClients();
      setError("");
    } catch (error) {
      setError("Erreur lors de la suppression");
    }
  };

  const SortIcon = ({ field }) => {
    if (sortField !== field) return null;
    return sortOrder === "asc" ? (
      <KeyboardArrowUp fontSize="small" />
    ) : (
      <KeyboardArrowDown fontSize="small" />
    );
  };

  return (
    <div className="container-fluid mt-4">
      {/* Search Card */}
      <Paper className="p-4 mb-4">
        <Typography variant="h6" className="mb-3">
          Recherche des clients
        </Typography>

        <Grid container spacing={2} sx={{ mb: 2 }}>
          <Grid item xs={12} md={4}>
            <TextField
              fullWidth
              label="Nom"
              variant="outlined"
              value={filters.nomLike || ""}
              onChange={(e) =>
                setFilters({ ...filters, nomLike: e.target.value })
              }
            />
          </Grid>
          <Grid item xs={12} md={4}>
            <TextField
              fullWidth
              label="Prénom"
              variant="outlined"
              value={filters.prenomLike || ""}
              onChange={(e) =>
                setFilters({ ...filters, prenomLike: e.target.value })
              }
            />
          </Grid>
          <Grid item xs={12} md={4}>
            <TextField
              fullWidth
              label="Email"
              variant="outlined"
              value={filters.emailLike || ""}
              onChange={(e) =>
                setFilters({ ...filters, emailLike: e.target.value })
              }
            />
          </Grid>
        </Grid>
      
        

        <div className="flex justify-end">
          <Button
            variant="outlined"
            startIcon={<Refresh />}
            onClick={() => {
              setFilters({ nomLike: "", prenomLike: "", emailLike: "" });
              fetchClients();
            }}
            className="me-2"
          >
            Réinitialiser
          </Button>
          <Button
            variant="contained"
            startIcon={<Search />}
            onClick={fetchClients}
          >
            Rechercher
          </Button>
        </div>
      </Paper>

      {/* Results Card */}
      <Paper className="p-4">
        <div className="d-flex justify-content-between align-items-center mb-3">
          <Typography variant="h6">Liste des clients ({total})</Typography>
          <div className="flex justify-end">
            {selectedClients.length > 0 && (
              <IconButton
                color="error"
                onClick={() => setDeleteDialogOpen(true)}
                className="me-2"
              >
                <Delete />
              </IconButton>
            )}
            <Button
              variant="contained"
              startIcon={<Add />}
              component={Link}
              href="/crud_Client/Add_Edit_Client"
            >
              Ajouter
            </Button>
          </div>
        </div>

        {error && (
          <Alert severity="error" className="mb-3">
            {error}
          </Alert>
        )}

        {loading ? (
          <div className="text-center">
            <CircularProgress />
          </div>
        ) : (
          <TableContainer>
            <Table>
              <TableHead>
                <TableRow>
                  <TableCell padding="checkbox">
                    <Checkbox
                      checked={
                        clients.length > 0 &&
                        selectedClients.length === clients.length
                      }
                      onChange={handleSelectAll}
                    />
                  </TableCell>
                  <TableCell onClick={() => handleSort("nom")}>
                    Nom <SortIcon field="nom" />
                  </TableCell>
                  <TableCell onClick={() => handleSort("prenom")}>
                    Prenom <SortIcon field="prenom" />
                  </TableCell>
                  <TableCell onClick={() => handleSort("email")}>
                    Email <SortIcon field="email" />
                  </TableCell>
                  <TableCell align="center">Actions</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {clients.map((client) => (
                  <TableRow key={client.id} hover>
                    <TableCell padding="checkbox">
                      <Checkbox
                        checked={selectedClients.some(
                          (h) => h.id === client.id
                        )}
                        onChange={() => handleSelectClient(client)}
                      />
                    </TableCell>
                    <TableCell>{client.nom}</TableCell>
                    <TableCell>{client.prenom}</TableCell>
                    <TableCell>{client.email}</TableCell>
                    <TableCell align="center">
                      <IconButton
                        component={Link}
                        href={{
                          pathname: "/crud_Client/View_Client",
                          query: { id: client.id },
                        }}
                        color="primary"
                      >
                        <Visibility />
                      </IconButton>
                      <IconButton
                        component={Link}
                        href={{
                          pathname: "/crud_Client/Add_Edit_Client",
                          query: { id: client.id },
                        }}
                        color="secondary"
                      >
                        <Edit />
                      </IconButton>
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
        )}

        <div className="d-flex justify-content-between align-items-center mt-3">
          <div>
            <select
              className="form-select"
              value={rowsPerPage}
              onChange={(e) => setRowsPerPage(Number(e.target.value))}
            >
              {[5, 10, 50].map((size) => (
                <option key={size} value={size}>
                  {size} par page
                </option>
              ))}
            </select>
          </div>
          <div className="flex justify-end">
            <Button disabled={page === 0} onClick={() => setPage((p) => p - 1)}>
              Précédent
            </Button>
            <span className="mx-2">
              Page {page + 1} sur {Math.ceil(total / rowsPerPage)}
            </span>
            <Button
              disabled={page >= Math.ceil(total / rowsPerPage) - 1}
              onClick={() => setPage((p) => p + 1)}
            >
              Suivant
            </Button>
          </div>
        </div>
      </Paper>

      {/* Delete Dialog */}
      <Dialog
        open={deleteDialogOpen}
        onClose={() => setDeleteDialogOpen(false)}
      >
        <DialogTitle>Voulez-vous supprimer ces éléments ?</DialogTitle>
        <DialogContent>
          <DialogContentText>
            {selectedClients.length === 1
              ? "Un Client va être supprimé"
              : `${selectedClients.length} clients vont être supprimés`}
          </DialogContentText>
          <DialogContentText>
            <b>NB :</b> Tous les demandes de cette client vont étre supprimer
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setDeleteDialogOpen(false)}>Annuler</Button>
          <Button onClick={handleDelete} color="error">
            Supprimer
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}
