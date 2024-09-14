import React, { createContext, useState, useContext } from 'react';

const PrevMessageContext = createContext(null);

export const PrevMessageProvider = ({ children }) => {
  const [prevMessage, setPrevMessage] = useState(null);

  return (
    <PrevMessageContext.Provider value={{ prevMessage, setPrevMessage }}>
      {children}
    </PrevMessageContext.Provider>
  );
};
  
export const usePrevMessage = () => {
  return useContext(PrevMessageContext);
};
