import React, { useState, useEffect } from 'react';
import {Link, Box, Select, MenuItem, InputAdornment} from '@mui/material';
import PersonList from './personList';
import Header from '../../components/header/Header';
import SearchIcon from '@mui/icons-material/Search';
import './EnterYourLocation.css'
import {useNavigate} from 'react-router-dom';
import AddLocationAltOutlinedIcon from '@mui/icons-material/AddLocationAltOutlined';



const EnterYourLocation = () => {

  const locations = PersonList.map((person) => person.location);
  const [selectedLocation, setSelectedLocation] = useState('');

  useEffect(() => {
    console.log(selectedLocation);
  }, [selectedLocation]);

  const handleLocationChange = (event) => {
    setSelectedLocation(event.target.value);
  };

  const navigate = useNavigate();

  return (

    <Box>
      <Header text='Enter Your Location' />
      <AddLocationAltOutlinedIcon sx={{ marginTop: '20px', width:'40', height:'40'}}/>
      <Box sx={{ display: 'flex', justifyContent: 'space-evenly' ,alignItems: 'center' }}>
    
      <Select
        value={selectedLocation}
        onChange={handleLocationChange}
        sx={{ marginTop: '20px', minWidth: 500 }}
        MenuProps={{
          MenuListProps: { disablePadding: true }
        }}

        startAdornment={(
          <InputAdornment position="start">
            <SearchIcon />
          </InputAdornment>
        )}
      >

          
        {locations.map(location => (
          <MenuItem 
          onClick={() => navigate("/search-services")}
          key={location} 
          value={location}>
          {location}
          </MenuItem>
        ))}
      </Select>
      </Box>
    </Box>
  );
}

export default EnterYourLocation;
