import { useState, useEffect } from 'react';
import { useRouter } from 'next/navigation';
import { getAuth, saveAuth, logOut, saveTokens } from '../util/authUtil';
import { FullInfoUser, TokenResponse } from '@/types/auth';

const useCurrentUser = () => {
  const [currentUser, setCurrentUser] = useState<FullInfoUser | null>(null);
  const [isAuthenticated, setIsAuthenticated] = useState<boolean>(false);
  const router = useRouter();

  useEffect(() => {
    const { user } = getAuth();
    if (user) {
      setCurrentUser(user);
      setIsAuthenticated(true);
    }
  }, []);

  const refreshUser = async () => {
    // Replace with your logic to fetch the latest user data
    const response = await fetch('/api/user');
    if (response.ok) {
      const updatedUser: FullInfoUser = await response.json();
      saveAuth(updatedUser);
      setCurrentUser(updatedUser);
    } else {
      throw new Error('Failed to refresh user data');
    }
  };

  const updateUser = async (updatedData: FullInfoUser) => {
    // Replace with your logic to update user data
    const response = await fetch('/api/user', {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(updatedData),
    });

    if (response.ok) {
      const updatedUser: FullInfoUser = await response.json();
      saveAuth(updatedUser);
      setCurrentUser(updatedUser);
    } else {
      throw new Error('Failed to update user data');
    }
  };

  return {
    currentUser,
    isAuthenticated,
    refreshUser,
    updateUser,
  };
};

export default useCurrentUser;