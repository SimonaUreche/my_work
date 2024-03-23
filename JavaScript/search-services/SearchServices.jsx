import React from 'react';
import { Box, Paper, InputBase, IconButton } from '@mui/material';
import SearchIcon from '@mui/icons-material/Search';
import './SearchServices.css'; 
import MyCard from '../../components/card/MyCard';
import personList from '../enter-your-location/personList';
import {useNavigate} from 'react-router-dom';

const SearchServices = () => {

  const navigate = useNavigate();

  return (
    <Box className='full-screen-container'>
      <Box className='search-container'>
        <Paper component="form" sx={{ p: '2px 4px', display: 'flex', alignItems: 'center', width: '30%' }}>
          <InputBase
            sx={{ ml: 1, flex: 1 }}
            placeholder="Search..."
            inputProps={{ 'aria-label': 'search' }}
          />
          <IconButton 
          onClick={() => navigate("/confirm-adress")}
          type="submit" 
          aria-label="search">
            <SearchIcon />
          </IconButton>
        </Paper>
      </Box>
   
      <Box className='gmap-frame'>
        <iframe
          id="gmap_canvas"
          width="100%"
          height="1000" 
          frameBorder="0"
          scrolling="no"
          marginHeight="0"
          marginWidth="0"
          src="https://maps.google.com/maps?width=100%25&amp;height=600&amp;hl=en&amp;q=Maieru+(My%20Map)&amp;t=h&amp;z=14&amp;ie=UTF8&amp;iwloc=B&amp;output=embed">
        </iframe>
      </Box>

      <Box className='card-container'>
      {personList.map(person => (
        <div className='card' key={person.id}>
          <MyCard
            title={person.name}
            details={person.about}
            bgcolor="transparent"
            stars={person.review.stars}
            location={person.location}
            category={person.category}
            image={person.image}
          />
        </div>
      ))}
    </Box>


    </Box>
  );
}

export default SearchServices;

