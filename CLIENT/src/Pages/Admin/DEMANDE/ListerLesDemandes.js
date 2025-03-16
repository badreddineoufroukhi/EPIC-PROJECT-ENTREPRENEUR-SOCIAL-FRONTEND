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
  Grid,
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

export default function DemandeList() {
  const [demandes, setDemandes] = useState([]);
  const [loading, setLoading] = useState(false);
  const [total, setTotal] = useState(0);
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(5);
  const [selectedDemandes, setSelectedDemandes] = useState([]);
  const [deleteDialogOpen, setDeleteDialogOpen] = useState(false);
  const [sortField, setSortField] = useState("");
  const [sortOrder, setSortOrder] = useState("desc");
  const [filters, setFilters] = useState({});
  const [error, setError] = useState("");

  const fetchDemandes = async () => {
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
        "http://localhost:8080/demande/paginatedListByCriteria",
        payload,
        {
          headers: { "Content-Type": "application/json" },
        }
      );

      const data = response.data;
      setDemandes(data.list || []);
      setTotal(data.dataSize || 0);
      setError("");
    } catch (error) {
      console.error("Error fetching demandes:", error);
      setError("Erreur lors du chargement des demandes");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchDemandes();
  }, [page, rowsPerPage, sortField, sortOrder]);

  const handleSort = (column) => {
    const isAsc = sortField === column && sortOrder === "asc";
    setSortOrder(isAsc ? "desc" : "asc");
    setSortField(column);
  };

  const handleSelectAll = (event) => {
    setSelectedDemandes(event.target.checked ? demandes : []);
  };

  const handleSelectDemande = (demande) => {
    setSelectedDemandes((prevSelected) => {
      const isSelected = prevSelected.find((h) => h.id === demande.id);
      return isSelected
        ? prevSelected.filter((h) => h.id !== demande.id)
        : [...prevSelected, demande];
    });
  };

  const handleDelete = async () => {
    const selectedDemandesToSend = selectedDemandes.map(
      (selectedDemande) => selectedDemande.id
    );
    try {
      await fetch("http://localhost:8080/demande/delete", {
        method: "DELETE",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(selectedDemandesToSend),
      });
      setDeleteDialogOpen(false);
      setSelectedDemandes([]);
      fetchDemandes();
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
          Recherche des demandes
        </Typography>

        <Grid container spacing={2} sx={{ mb: 2 }}>
          <Grid item xs={12} md={6}>
            <TextField
              fullWidth
              label="Description"
              variant="outlined"
              value={filters.descriptionLike || ""}
              onChange={(e) =>
                setFilters({ ...filters, descriptionLike: e.target.value })
              }
            />
          </Grid>
          <Grid item xs={12} md={6}>
            <TextField
              fullWidth
              label="Rating"
              type="number"
              variant="outlined"
              value={filters.rating || ""}
              onChange={(e) =>
                setFilters({ ...filters, rating: e.target.value })
              }
            />
          </Grid>
        </Grid>

        <div className="flex justify-end">
          <Button
            variant="outlined"
            startIcon={<Refresh />}
            onClick={() => {
              setFilters({
                nomLike: "",
                prenomLike: "",
                rating: 0,
                emailLike: "",
                professionLike: "",
                numeroTelephoneLike: "",
                villeLike: "",
                descriptionLike: "",
              });
              fetchDemandes();
            }}
            className="me-2"
          >
            Réinitialiser
          </Button>
          <Button
            variant="contained"
            startIcon={<Search />}
            onClick={fetchDemandes}
          >
            Rechercher
          </Button>
        </div>
      </Paper>

      {/* Results Card */}
      <Paper className="p-4">
        <div className="d-flex justify-content-between align-items-center mb-3">
          <Typography variant="h6">Liste des demandes ({total})</Typography>
          <div className="flex justify-end">
            {selectedDemandes.length > 0 && (
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
              href="/crud_Demande/Add_Edit_Demande"
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
                        demandes.length > 0 &&
                        selectedDemandes.length === demandes.length
                      }
                      onChange={handleSelectAll}
                    />
                  </TableCell>
                  <TableCell onClick={() => handleSort("servicePropose.name")}>
                    Nom de service propose{" "}
                    <SortIcon field="servicePropose.name" />
                  </TableCell>
                  <TableCell onClick={() => handleSort("client.nom")}>
                    Nom de client <SortIcon field="client.nom" />
                  </TableCell>
                  <TableCell onClick={() => handleSort("client.prenom")}>
                    Prenom de client <SortIcon field="client.prenom" />
                  </TableCell>
                  <TableCell onClick={() => handleSort("rating")}>
                    Rating <SortIcon field="rating" />
                  </TableCell>
                  <TableCell onClick={() => handleSort("agreement")}>
                    Agreement <SortIcon field="agreement" />
                  </TableCell>
                  <TableCell onClick={() => handleSort("description")}>
                    Description <SortIcon field="description" />
                  </TableCell>
                  <TableCell align="center">Actions</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {demandes.map((demande) => (
                  <TableRow key={demande.id} hover>
                    <TableCell padding="checkbox">
                      <Checkbox
                        checked={selectedDemandes.some(
                          (h) => h.id === demande.id
                        )}
                        onChange={() => handleSelectDemande(demande)}
                      />
                    </TableCell>
                    <TableCell>{demande.servicePropose.name}</TableCell>
                    <TableCell>{demande.client.nom}</TableCell>
                    <TableCell>{demande.client.prenom}</TableCell>
                    <TableCell>{demande.rating}</TableCell>
                    <TableCell>{demande.agreement}</TableCell>
                    <TableCell>{demande.description}</TableCell>
                    <TableCell align="center">
                      <IconButton
                        component={Link}
                        href={{
                          pathname: "/crud_Demande/View_Demande",
                          query: { id: demande.id },
                        }}
                        color="primary"
                      >
                        <Visibility />
                      </IconButton>
                      <IconButton
                        component={Link}
                        href={{
                          pathname: "/crud_Demande/Add_Edit_Demande",
                          query: { id: demande.id },
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
            {selectedDemandes.length === 1
              ? "Un demande va être supprimé"
              : `${selectedDemandes.length} demandes vont être supprimés`}
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
