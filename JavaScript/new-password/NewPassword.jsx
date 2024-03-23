import React, { useState } from 'react';
import Header from '../../components/header/Header';
import './NewPassword.css'; 
import {Button, Box, Typography } from '@mui/material'; 

const NewPassword = () => {
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log(`Parola nouă: ${password}`);
    console.log(`Confirmare parolă: ${confirmPassword}`);
    
  };
  

  const passwordsMatch = password === confirmPassword;
  const passwordsMatch2 = (password === ' ' && confirmPassword == ' ');


  return (
    <Box>
      <Header text="New Password" />

      <Typography sx={{ fontSize: '15px', marginTop: '10px', marginBottom: '2px' }} variant="h6" className="password-heading">
      Your new password be different from previously used passwords
      </Typography>

      <form onSubmit={handleSubmit} className="password-form">
        <label className="form-label">

     
        <label  hidden for="password-input">Password</label>
          <input
             name='password-input'
            placeholder='New Password'
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
            className="form-input"
          />
        </label>
       

        <label className="form-label">
        <label  hidden for="confirm-password-input">Confirm Password</label>
          <input
            name='confirm-password-input'
            placeholder='Confirm Password'
            type="password"
            value={confirmPassword}
            onChange={(e) => setConfirmPassword(e.target.value)}
            required
            className="form-input"
          />
        </label>
        
      
        <button type="submit" className="form-button" disabled={!passwordsMatch && passwordsMatch2}>Create New Password</button>

      </form>
    </Box>
  );

};

export default NewPassword;
