# Ofbiz perl helper 
package Ofbiz;

use strict;
use warnings 'all';
no warnings 'deprecated';
use XML::Simple ();
use fields qw(conf datasource dbhost dbname dbuser dbpass dbport);

sub new {
    my $self = shift;
    my %args = (@_);
    unless (-f $args{conf} && $args{datasource}) {
	warn "usage: Ofbiz->new(conf => '/path/to/entityengine.xml',
                                datasource => 'localmysql')";
	return undef;
    }
    unless (ref $self) {
	$self = fields::new($self);
	$self->{conf} = $args{conf};
	$self->{datasource} = $args{datasource};
	$self->{dbhost} = undef;
	$self->{dbname} = undef;
	$self->{dbuser} = undef;
	$self->{dbpass} = undef;
	$self->{dbport} = undef;
    }

    return $self->parse_conf();
}

sub parse_conf {
    my $self = shift;
    
    my $ref = XML::Simple::XMLin($self->{conf}) or return undef;
    my $inline = $ref->{'datasource'}->{$self->{'datasource'}}->{'inline-jdbc'} or return undef;

    my $uri = $inline->{'jdbc-uri'};
    if ($uri =~ m|jdbc:\w+?://(.+?)/(.+?)\?|) {
	my $host = $1;
	my $name = $2;
	if ($host =~ m/^(.+?):(\w+)$/) {
	    $self->{dbhost} = $1;
	    $self->{dbport} = $2;
	} else {
	    $self->{dbhost} = $host;
	    $self->{dbport} = ''
	}
	$self->{dbname} = $name;
    } else {
	warn "could not extract dbhost and dbname from jdbc-uri: $uri";
	return undef;
    }
    $self->{dbuser} = $inline->{'jdbc-username'};
    $self->{dbpass} = $inline->{'jdbc-password'};

    return $self;
}

sub dsn {
    my $self = shift;
    my $type = shift;
    my $dsn = "DBI:$type:database=" . $self->{dbname} . ";host=" . $self->{dbhost};
    $dsn .= ";port=" . $self->{dbport} if ($self->{dbport});
    return $dsn;
}

1;

__END__

'inline-jdbc' => {
   'pool-minsize' => '8',
   'jdbc-username' => 'ssm_prd',
   'isolation-level' => 'ReadCommitted',
   'jdbc-driver' => 'com.mysql.jdbc.Driver',
   'jdbc-uri' => 'jdbc:mysql://db1/ssm_prd?autoReconnect=true',
   'jdbc-password' => 'lLV9bgnD',
   'pool-maxsize' => '40'
   }
