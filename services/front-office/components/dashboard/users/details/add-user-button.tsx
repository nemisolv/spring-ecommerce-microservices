'use client';

import * as React from 'react';
import NiceModal from '@ebay/nice-modal-react';

import { AddContactModal } from '@/components/dashboard/contacts/add-contact-modal';
import { Button } from '@/components/ui/button';

export function AddContactButton(): React.JSX.Element {
  const handleShowAddContactModal = (): void => {
    NiceModal.show(AddContactModal);
  };
  return (
    <Button
      type="button"
      variant="default"
      size="default"
      className="whitespace-nowrap"
      onClick={handleShowAddContactModal}
    >
      Add contact
    </Button>
  );
}
