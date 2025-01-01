import * as React from 'react';
import Link from 'next/link';
import { ArrowLeftIcon } from 'lucide-react';

import { buttonVariants } from '@/components/ui/button';
import { ScrollArea } from '@/components/ui/scroll-area';
import { SidebarTrigger } from '@/components/ui/sidebar';
import { cn } from '@/lib/utils';

export type PageElement = HTMLDivElement;
export type PageProps = React.HTMLAttributes<HTMLDivElement>;
const Page = React.forwardRef<PageElement, PageProps>(
  ({ children, className, ...other }, ref) => (
    <div
      ref={ref}
      className={cn('flex h-full flex-col', className)}
      {...other}
    >
      {children}
    </div>
  )
);
Page.displayName = 'Page';

export type PageHeaderElement = HTMLDivElement;
export type PageHeaderProps = React.HTMLAttributes<HTMLDivElement>;
const PageHeader = React.forwardRef<PageHeaderElement, PageHeaderProps>(
  ({ className, children, ...other }, ref) => (
    <div
      ref={ref}
      className={cn('sticky top-0 z-20 bg-background', className)}
      {...other}
    >
      {children}
    </div>
  )
);
PageHeader.displayName = 'PageHeader';

export type PagePrimaryBarElement = HTMLDivElement;
export type PagePrimaryBarProps = React.HTMLAttributes<HTMLDivElement>;
const PagePrimaryBar = React.forwardRef<
  PagePrimaryBarElement,
  PagePrimaryBarProps
>(({ className, children, ...other }, ref) => (
  <div
    ref={ref}
    className={cn(
      'relative flex h-16 flex-row items-center gap-3 border-b px-6',
      className
    )}
    {...other}
  >
    <SidebarTrigger
      icon="menu"
      className="-ml-2 lg:hidden"
    />
    <div className="flex w-full flex-row items-center justify-between">
      {children}
    </div>
  </div>
));
PagePrimaryBar.displayName = 'PagePrimaryBar';

export type PageTitleElement = HTMLHeadingElement;
export type PageTitleProps = React.HTMLAttributes<HTMLHeadingElement>;
const PageTitle = React.forwardRef<PageTitleElement, PageTitleProps>(
  ({ className, children, ...other }, ref) => (
    <h1
      ref={ref}
      className={cn('text-base font-semibold', className)}
      {...other}
    >
      {children}
    </h1>
  )
);
PageTitle.displayName = 'PageTitle';

export type PageBackElement = React.ElementRef<typeof Link>;
export type PageBackProps = React.ComponentPropsWithoutRef<typeof Link>;
const PageBack = React.forwardRef<PageBackElement, PageBackProps>(
  ({ className, ...other }, ref) => (
    <Link
      ref={ref}
      title="Back"
      className={cn(
        buttonVariants({ variant: 'outline', size: 'icon' }),
        'hidden size-7 sm:flex',
        className
      )}
      {...other}
    >
      <ArrowLeftIcon className="size-4 shrink-0" />
      <span className="sr-only">Back</span>
    </Link>
  )
);
PageBack.displayName = 'PageBack';

export type PageActionsElement = HTMLDivElement;
export type PageActionsProps = React.HTMLAttributes<HTMLDivElement>;
const PageActions = React.forwardRef<PageActionsElement, PageActionsProps>(
  ({ className, children, ...other }, ref) => (
    <div
      ref={ref}
      className={cn('flex items-center gap-2', className)}
      {...other}
    >
      {children}
    </div>
  )
);
PageActions.displayName = 'PageActions';

export type PageSecondaryBarElement = HTMLDivElement;
export type PageSecondaryBarProps = React.HTMLAttributes<HTMLDivElement>;
const PageSecondaryBar = React.forwardRef<
  PageSecondaryBarElement,
  PageSecondaryBarProps
>(({ className, children, ...other }, ref) => (
  <div
    ref={ref}
    className={cn(
      'relative flex h-12 items-center justify-between gap-2 border-b px-6',
      className
    )}
    {...other}
  >
    {children}
  </div>
));
PageSecondaryBar.displayName = 'PageSecondaryBar';

export type PageBodyElement = HTMLDivElement;
export type PageBodyProps = React.HTMLAttributes<HTMLDivElement> & {
  disableScroll?: boolean;
};
const PageBody = React.forwardRef<PageBodyElement, PageBodyProps>(
  ({ children, className, disableScroll = false, ...other }, ref) => {
    if (disableScroll) {
      return (
        <div
          className={cn('flex h-full flex-col', className)}
          ref={ref}
          {...other}
        >
          {children}
        </div>
      );
    }

    return (
      <div
        className={cn('grow overflow-hidden', className)}
        ref={ref}
        {...other}
      >
        <ScrollArea className="h-full">{children}</ScrollArea>
      </div>
    );
  }
);
PageBody.displayName = 'PageBody';

export {
  Page,
  PageActions,
  PageBack,
  PageBody,
  PageHeader,
  PagePrimaryBar,
  PageSecondaryBar,
  PageTitle
};
