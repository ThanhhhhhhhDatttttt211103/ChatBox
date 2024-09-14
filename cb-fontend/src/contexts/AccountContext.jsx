import React, { createContext, useState, useContext } from 'react';

const AccountContext = createContext(null);

export const AccountProvider = ({ children }) => {
  const [account, setAccount] = useState(null);

  return (
    <AccountContext.Provider value={{ account, setAccount }}>
      {children}
    </AccountContext.Provider>
  );
};
  
export const useAccount = () => {
  return useContext(AccountContext);
};
