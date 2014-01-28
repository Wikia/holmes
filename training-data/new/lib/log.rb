

class LogConfig
  @@logs_enabled = true

  def self.enable_logs
    @@logs_enabled = true
  end

  def self.disable_logs
    @@logs_enabled = false
  end

  def self.logs_enabled?
    return @@logs_enabled
  end
end

def log( str )
  if LogConfig.logs_enabled?
    STDERR.write("#{str}\n")
  end
end
