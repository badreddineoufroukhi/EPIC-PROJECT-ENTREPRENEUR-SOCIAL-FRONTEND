"use client";
import React, { useState, useEffect } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import { Chip } from "@mui/material";

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

export default function OuvrierList() {
  const [ouvriers, setOuvriers] = useState([]);
  const [loading, setLoading] = useState(false);
  const [total, setTotal] = useState(0);
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(5);
  const [selectedOuvriers, setSelectedOuvriers] = useState([]);
  const [deleteDialogOpen, setDeleteDialogOpen] = useState(false);
  const [sortField, setSortField] = useState("");
  const [sortOrder, setSortOrder] = useState("desc");
  const [filters, setFilters] = useState({});
  const [error, setError] = useState("");

  const fetchOuvriers = async () => {
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
        "http://localhost:8080/ouvrier/paginatedListByCriteria",
        payload,
        {
          headers: { "Content-Type": "application/json" },
        }
      );

      const data = response.data;
      setOuvriers(data.list || []);
      setTotal(data.dataSize || 0);
      setError("");
    } catch (error) {
      console.error("Error fetching ouvriers:", error);
      setError("Erreur lors du chargement des OUVRIERS");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchOuvriers();
  }, [page, rowsPerPage, sortField, sortOrder]);

  const handleSort = (column) => {
    const isAsc = sortField === column && sortOrder === "asc";
    setSortOrder(isAsc ? "desc" : "asc");
    setSortField(column);
  };

  const handleSelectAll = (event) => {
    setSelectedOuvriers(event.target.checked ? ouvriers : []);
  };

  const handleSelectOuvrier = (ouvrier) => {
    setSelectedOuvriers((prevSelected) => {
      const isSelected = prevSelected.find((h) => h.id === ouvrier.id);
      return isSelected
        ? prevSelected.filter((h) => h.id !== ouvrier.id)
        : [...prevSelected, ouvrier];
    });
  };

  const handleDelete = async () => {
    const selectedOuvriersToSend = selectedOuvriers.map(
      (selectedOuvrier) => selectedOuvrier.id
    );
    try {
      await fetch("http://localhost:8080/ouvrier/delete", {
        method: "DELETE",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(selectedOuvriersToSend),
      });
      setDeleteDialogOpen(false);
      setSelectedOuvriers([]);
      fetchOuvriers();
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
          Recherche des ouvriers
        </Typography>

        <Grid container spacing={2} sx={{ mb: 2 }}>
          <Grid item xs={12} md={3}>
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
          <Grid item xs={12} md={3}>
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
          <Grid item xs={12} md={3}>
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
          <Grid item xs={12} md={3}>
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
        <Grid container spacing={2} sx={{ mb: 2 }}>
          <Grid item xs={12} md={3}>
            <TextField
              fullWidth
              label="Profession"
              variant="outlined"
              value={filters.professionLike || ""}
              onChange={(e) =>
                setFilters({ ...filters, professionLike: e.target.value })
              }
            />
          </Grid>
          <Grid item xs={12} md={3}>
            <TextField
              fullWidth
              label="Numero de telephone"
              variant="outlined"
              value={filters.numeroTelephoneLike || ""}
              onChange={(e) =>
                setFilters({ ...filters, numeroTelephoneLike: e.target.value })
              }
            />
          </Grid>
          <Grid item xs={12} md={3}>
            <TextField
              fullWidth
              label="Ville"
              variant="outlined"
              value={filters.villeLike || ""}
              onChange={(e) =>
                setFilters({ ...filters, villeLike: e.target.value })
              }
            />
          </Grid>
          <Grid item xs={12} md={3}>
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
        </Grid>
        

        <div className="flex justify-end">
          <Button
            variant="outlined"
            startIcon={<Refresh />}
            onClick={() => {
              setFilters({ nomLike: "", prenomLike: "", rating: 0, emailLike: "", professionLike: "", numeroTelephoneLike: "", villeLike: "", descriptionLike: "" });
              fetchOuvriers();
            }}
            className="me-2"
          >
            Réinitialiser
          </Button>
          <Button
            variant="contained"
            startIcon={<Search />}
            onClick={fetchOuvriers}
          >
            Rechercher
          </Button>
        </div>
      </Paper>

      {/* Results Card */}
      <Paper className="p-4">
        <div className="d-flex justify-content-between align-items-center mb-3">
          <Typography variant="h6">Liste des ouvriers ({total})</Typography>
          <div className="flex justify-end">
            {selectedOuvriers.length > 0 && (
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
              href="/crud_Ouvrier/Add_Edit_Ouvrier"
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
                        ouvriers.length > 0 &&
                        selectedOuvriers.length === ouvriers.length
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
                  <TableCell onClick={() => handleSort("profession")}>
                    Profession <SortIcon field="profession" />
                  </TableCell>
                  <TableCell onClick={() => handleSort("numeroTelephone")}>
                    NumeroTelephone <SortIcon field="numeroTelephone" />
                  </TableCell>
                  <TableCell onClick={() => handleSort("rating")}>
                    Rating <SortIcon field="rating" />
                  </TableCell>
                  <TableCell onClick={() => handleSort("ville")}>
                    Ville <SortIcon field="ville" />
                  </TableCell>
                  <TableCell onClick={() => handleSort("disponible")}>
                    Disponible <SortIcon field="disponible" />
                  </TableCell>
                  <TableCell onClick={() => handleSort("description")}>
                    Description <SortIcon field="description" />
                  </TableCell>
                  <TableCell align="center">Actions</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {ouvriers.map((ouvrier) => (
                  <TableRow key={ouvrier.id} hover>
                    <TableCell padding="checkbox">
                      <Checkbox
                        checked={selectedOuvriers.some(
                          (h) => h.id === ouvrier.id
                        )}
                        onChange={() => handleSelectOuvrier(ouvrier)}
                      />
                    </TableCell>
                    <TableCell>{ouvrier.nom}</TableCell>
                    <TableCell>{ouvrier.prenom}</TableCell>
                    <TableCell>{ouvrier.email}</TableCell>
                    <TableCell>{ouvrier.profession}</TableCell>
                    <TableCell>{ouvrier.numeroTelephone}</TableCell>
                    <TableCell>{ouvrier.rating}</TableCell>
                    <TableCell>{ouvrier.ville}</TableCell>
                    <TableCell>
                      <Chip
                        label={
                          ouvrier.disponible ? "Disponible" : "Indisponible"
                        }
                        color={ouvrier.disponible ? "success" : "error"}
                        variant="outlined"
                      />
                    </TableCell>{" "}
                    <TableCell>{ouvrier.description}</TableCell>
                    <TableCell align="center">
                      <IconButton
                        component={Link}
                        href={{
                          pathname: "/crud_Ouvrier/View_Ouvrier",
                          query: { id: ouvrier.id },
                        }}
                        color="primary"
                      >
                        <Visibility />
                      </IconButton>
                      <IconButton
                        component={Link}
                        href={{
                          pathname: "/crud_Ouvrier/Add_Edit_Ouvrier",
                          query: { id: ouvrier.id },
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
            {selectedOuvriers.length === 1
              ? "Un ouvrier va être supprimé"
              : `${selectedOuvriers.length} ouvriers vont être supprimés`}
          </DialogContentText>
          <DialogContentText>
            <b>NB :</b> Tous les sous services et les demandes <br/>de cette ouvrier vont étre supprimer
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
